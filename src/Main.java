import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        HashMap<String, ClassRoom> listClassRooms = Reader.getClassrooms();
        HashMap<String, Group> listGroups = Reader.getGroups();
        HashMap<String, Integer> listDistances = Reader.getDistances();
        if(listClassRooms != null && listGroups != null && listDistances != null){
            System.out.println("todo bien");
        }
        else{
            System.out.println("Algo fallo");
        }
    }
}
