import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Lesson {

    static HashMap<String, Lesson>[] lessonPerDay = new HashMap[7];
    private boolean isVisited = false;
    static boolean check = false;
    private final char day;
    private final String startingTime;
    private final String endingTime;
    private String classroom;
    private final String idGroup;
    private ArrayList<Lesson> prevLessons = new ArrayList<>();
    private String newClassroom;

    public Lesson(char day, String startingTime, String endingTime, String classroom, String idGroup) {
        this.day = day;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
        this.classroom = classroom;
        this.idGroup = idGroup;
        addLesson(this);
    }

    public String getNewClassroom() {
        return newClassroom;
    }

    public void setNewClassroom(String newClassroom) {
        this.newClassroom = newClassroom;
    }

    public char getCharDay(){
        return this.day;
    }

    public String getClassroom() {
        return classroom;
    }

    public void addPrevLesson(Lesson lesson){
        this.prevLessons.add(lesson);
    }

    public boolean hasPrevLesson(){
        return !this.prevLessons.isEmpty();
    }

    public int distanceRecorrida(ClassRoom cr){
        int distanceRecorrida = 0;
        for (Lesson lesson:this.prevLessons) {
            int otherBlock = Integer.parseInt(lesson.getNewClassroom().substring(0,2));
            if(cr.getBlock() == otherBlock){
                distanceRecorrida += 0;
            }
            else if(cr.getBlock() > otherBlock) {
                if(ClassRoom.listDistances.get(Integer.toString(cr.getBlock())+otherBlock) != null){
                    distanceRecorrida += ClassRoom.listDistances.get(Integer.toString(cr.getBlock())+otherBlock);
                }
            }
            else{
                if(ClassRoom.listDistances.get(otherBlock+Integer.toString(cr.getBlock())) != null){
                    distanceRecorrida += ClassRoom.listDistances.get(otherBlock+Integer.toString(cr.getBlock()));
                }
            }
        }
        return distanceRecorrida;
    }

    public String getStartingTime() {
        return this.startingTime;
    }

    public int getIntStart(){
        String start = this.startingTime;
        start = start.replaceAll(":", "");
        // System.out.println(start);
        return Integer.parseInt(start);
    }

    public int getIntEnd(){
        String end = this.endingTime;
        end = end.replaceAll(":", "");
        // System.out.println(start);
        return Integer.parseInt(end);
    }

    public String getIdGroup() {
        return this.idGroup;
    }

    public Lesson[] nextLessons(){
        ArrayList<Lesson> nextLessons = new ArrayList<>();
        for (Person p: Group.listGroups.get(this.idGroup).getListStudents().values()) {
            for(Group g : p.getGroups().values()){
                Lesson l = lessonPerDay[this.getDay()].get(g.getIdCourse() + g.getNumber() + this.endingTime);
                if( l != null && !nextLessons.contains(l)){
                    nextLessons.add(l);
                }
            }
        }
        /*System.out.println("Actual lesson: "+this + "Next lessons: ");
        for(Lesson less: nextLessons){
            System.out.print("\t"+less);
        }*/
        Lesson arr[] = new Lesson[nextLessons.size()];
        arr = nextLessons.toArray(arr);
        return arr;
    }

    public boolean isVisited(){
        return this.isVisited;
    }

    public void setVisit(boolean val){
        this.isVisited = val;
    }

    public int getDay(){
        switch(this.day){
            case 'L': return 0;
            case 'M': return 1;
            case 'W': return 2;
            case 'J': return 3;
            case 'V': return 4;
            case 'S': return 5;
            case 'D': return 6;
        }
        return -1;
    }
    public static void addLesson(Lesson lesson){
        if(!check){
            fillLessons();
            check = true;
        }
        switch (lesson.day) {
            case 'L':
                lessonPerDay[0].put(lesson.getIdGroup()+lesson.getStartingTime(), lesson);
                break;
            case 'M':
                lessonPerDay[1].put(lesson.getIdGroup()+lesson.getStartingTime(), lesson);
                break;
            case 'W':
                lessonPerDay[2].put(lesson.getIdGroup()+lesson.getStartingTime(), lesson);
                break;
            case 'J':
                lessonPerDay[3].put(lesson.getIdGroup()+lesson.getStartingTime(), lesson);
                break;
            case 'V':
                lessonPerDay[4].put(lesson.getIdGroup()+lesson.getStartingTime(), lesson);
                break;
            case 'S':
                lessonPerDay[5].put(lesson.getIdGroup()+lesson.getStartingTime(), lesson);
                break;
            case 'D':
                lessonPerDay[6].put(lesson.getIdGroup()+lesson.getStartingTime(), lesson);
                break;
        }
    }

    public static void fillLessons(){
        for(int i=0; i<lessonPerDay.length; i++){
            lessonPerDay[i] = new HashMap<String, Lesson>();
        }
    }

    @Override
    public String toString(){
        return "Id Group: " + this.idGroup +",Day : "+ day +", Start time: " + this.startingTime + ", End time: " + this.endingTime+ ", New Classroom: "+ this.newClassroom +"\n";
    }
    public static void cleanLesson(){
        for (int i = 0; i < 7; i++){
            HashMap<String, Lesson> map = lessonPerDay[i];
            Iterator<HashMap.Entry<String, Lesson>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Lesson> entry = it.next();
                if(Group.getGroup(entry.getValue().getIdGroup()) != null) {
                    if (!Group.getGroup(entry.getValue().getIdGroup()).hasPerson()) {
                        Group.listGroups.remove(entry.getValue().getIdGroup());
                        it.remove();
                    }
                }
                else{
                    it.remove();
                }
            }
        }
    }
}
