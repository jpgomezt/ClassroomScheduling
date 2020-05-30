import java.sql.Time;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        HashMap<String, ClassRoom> listClassRooms = Reader.getClassrooms();
        HashMap<String, Group> listGroups = Reader.getGroups();
        if(listClassRooms != null && listGroups != null){
            System.out.println("todo bien");
        }
        else{
            System.out.println("Algo fallo");
        }
    }
}
