import java.util.HashMap;

public class Lesson {

    static HashMap<String, Lesson>[] lessonPerDay = new HashMap[7];
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

    public String getIdGroup() {
        return idGroup;
    }

    public Lesson[] nextLessons(){
        for (Person p: Group.listGroups.get(idGroup).getListStudents().values()) {

        }
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
