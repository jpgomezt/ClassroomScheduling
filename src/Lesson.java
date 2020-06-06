import java.util.ArrayList;
import java.util.HashMap;

public class Lesson {

    static HashMap<String, Lesson>[] lessonPerDay = new HashMap[7];
    private boolean isVisited = false;
    static boolean check = false;
    private final char day;
    private final String startingTime;
    private final String endingTime;
    private String classroom;
    private final String idGroup;

    public Lesson(char day, String startingTime, String endingTime, String classroom, String idGroup) {
        this.day = day;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
        this.classroom = classroom;
        this.idGroup = idGroup;
        addLesson(this);
    }

    public String getStartingTime() {
        return startingTime;
    }

    public int getIntStart(){
        String start = startingTime;
        start = start.replaceAll(":", "");
        // System.out.println(start);
        return Integer.parseInt(start);
    }

    public String getIdGroup() {
        return idGroup;
    }

    public Lesson[] nextLessons(){
        ArrayList<Lesson> nextLessons = new ArrayList<>();
        for (Person p: Group.listGroups.get(idGroup).getListStudents().values()) {
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
        return "Id Group: " + this.idGroup +",Day : "+ day +", Start time: " + this.startingTime + ", End time: " + this.endingTime+"\n";
    }
    public static void cleanLesson(){
        for (int i = 0; i < 7; i++){
         HashMap<String,Lesson> hashMap= lessonPerDay[i];
            for (Lesson lesson:hashMap.values()) {
                if(Group.getGroup(lesson.getIdGroup()) != null) {
                    if(!Group.getGroup(lesson.getIdGroup()).hasPerson()) {
                        Group.listGroups.remove(lesson.getIdGroup());
                        hashMap.remove(lesson.getIdGroup() + lesson.getStartingTime());
                    }
                }
                else{
                    hashMap.remove(lesson.getIdGroup() + lesson.getStartingTime());
                }
            }
        }
    }
}
