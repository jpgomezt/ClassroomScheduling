import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Lesson {

    //Map with lessons each day
    static HashMap<String, Lesson>[] lessonPerDay = new HashMap[7];
    //Map with the lessons that are classroom 00000, or that doesnt have students
    static ArrayList<Lesson> irrelevantData = new ArrayList<>();
    //check if a lesson was visited
    private boolean isVisited = false;
    //Helper for lessonsPerDay
    private static boolean check = false;
    //Day of the lesson
    private final char day;
    //start time of the lesson
    private final String startingTime;
    //end time of the lesson
    private final String endingTime;
    //Initial classroom it had schedule
    private final String classroom;
    //Id of the group
    private final String idGroup;
    //Lessons that this lesson depends on
    private final ArrayList<Lesson> prevLessons = new ArrayList<>();
    //classroom we schedule
    private String newClassroom;
    //id of the professor
    private final int idProfessor;

    /**
     * Constructor of Lesson
     * @param day char
     * @param startingTime String
     * @param endingTime String
     * @param classroom String
     * @param idGroup String
     * @param idProfessor int
     */
    public Lesson(char day, String startingTime, String endingTime, String classroom, String idGroup, int idProfessor) {
        this.day = day;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
        this.classroom = classroom;
        this.idGroup = idGroup;
        this.idProfessor = idProfessor;
        if(!(classroom.equals("00000"))){
            addLesson(this);
        }
    }

    /**
     * Getter of the newClassroom
     * @return  String
     */
    public String getNewClassroom() {
        if(this.newClassroom == null){return "00000";}
        return newClassroom;
    }

    /**
     * Setter for a new classroom
     * @param newClassroom String
     */
    public void setNewClassroom(String newClassroom) {
        this.newClassroom = newClassroom;
    }

    /**
     * Getter for de day in char
     * @return char
     */
    public char getCharDay(){
        return this.day;
    }

    /**
     * Getter of the initial classroom
     * @return String
     */
    public String getClassroom() {
        return classroom;
    }

    /**
     * Adds Previous Lessons to the map
     * @param lesson Lesson
     */
    public void addPrevLesson(Lesson lesson){
        this.prevLessons.add(lesson);
    }

    /**
     * Checks if this lesson depends on prev lessons
     * @return boolean
     */
    public boolean hasPrevLesson(){
        return !this.prevLessons.isEmpty();
    }

    /**
     * Check the distance from one lesson to this
     * @param cr ClassRoom
     * @return int
     */
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

    /**
     * Getter of starting time
     * @return String
     */
    public String getStartingTime() {
        return this.startingTime;
    }

    /**
     * Getter of starting time in int
     * @return int
     */
    public int getIntStart(){
        String start = this.startingTime;
        start = start.replaceAll(":", "");
        // System.out.println(start);
        return Integer.parseInt(start);
    }

    /**
     * Getter of ending time in int
     * @return int
     */
    public int getIntEnd(){
        String end = this.endingTime;
        end = end.replaceAll(":", "");
        // System.out.println(start);
        return Integer.parseInt(end);
    }

    /**
     * Getter of id
     * @return String
     */
    public String getIdGroup() {
        return this.idGroup;
    }

    /**
     * Getter of next lessons
     * @return Lessons[]
     */
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
        Lesson[] arr = new Lesson[nextLessons.size()];
        arr = nextLessons.toArray(arr);
        return arr;
    }

    /**
     * Check if a lesson is visited
     * @return boolean
     */
    public boolean isVisited(){
        return this.isVisited;
    }

    /**
     * Setter for visited
     * @param val boolean
     */
    public void setVisit(boolean val){
        this.isVisited = val;
    }

    /**
     * Getter of day in int
     * @return int
     */
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

    /**
     * Add lesson to map
     * @param lesson Lesson
     */
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

    /**
     * Fill the array of lessonsPerDay
     */
    public static void fillLessons(){
        for(int i=0; i<lessonPerDay.length; i++){
            lessonPerDay[i] = new HashMap<String, Lesson>();
        }
    }

    /**
     * Getter of day in String
     * @return String
     */
    public String getDayString(){
        switch(this.day){
            case 'L': return "lunes";
            case 'M': return "martes";
            case 'W': return "miércoles";
            case 'V': return "viernes";
            case 'J': return "jueves";
            case 'S': return "sábado";
            case 'D': return "domingo";
        }
        return "Pailas";
    }

    /**
     * toString method
     * @return String
     */
    @Override
    public String toString(){
        return this.idGroup.substring(0,6) +","+ this.idGroup.substring(6) +"," +this.idProfessor + "," + this.getDayString() + "," + this.startingTime + ","+ this.endingTime +","+this.getNewClassroom();
    }

    /**
     * Cleans lessons that are not relevant
     */
    public static void cleanLesson(){
        for (int i = 0; i < 7; i++){
            HashMap<String, Lesson> map = lessonPerDay[i];
            Iterator<HashMap.Entry<String, Lesson>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Lesson> entry = it.next();
                if(Group.getGroup(entry.getValue().getIdGroup()) != null) {
                    if (!Group.getGroup(entry.getValue().getIdGroup()).hasPerson()) {
                        Group.listGroups.remove(entry.getValue().getIdGroup());
                        Lesson.irrelevantData.add(entry.getValue());
                        it.remove();
                    }
                }
                else{
                    Lesson.irrelevantData.add(entry.getValue());
                    it.remove();
                }
            }
        }
    }
}
