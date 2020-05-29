import java.sql.Time;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        //HashMap<Integer, Person> listStudents = Reader.getStudens();
        HashMap<String, ClassRoom> listClassRooms = Reader.getClassrooms();
        if(listClassRooms != null){
            System.out.println("todo bien");
        }
        else{
            System.out.println("Algo fallo");
        }
    }
}
