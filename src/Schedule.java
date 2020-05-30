import java.util.HashMap;
public class Schedule{

    static HashMap<String, ClassRoom> listClassRooms = Reader.getClassrooms();
    static HashMap<String, Group> listGroups = Reader.getGroups();
    static HashMap<String, Integer> listDistances = Reader.getDistances();
    static HashMap<String, Group>[] lessonPerDay = Reader.lessonPerDay;

    public static void scheduling(){
        listClassRooms = Reader.getClassrooms();
        listGroups = Reader.getGroups();
        listDistances = Reader.getDistances();
        lessonPerDay = Reader.lessonPerDay;

        for(int i=0; i < 7, i++){

        }
    }
}
