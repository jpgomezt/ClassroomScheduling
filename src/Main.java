import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        Reader.read();
        if(ClassRoom.listClassRooms != null && Group.listGroups != null && Person.listPerson != null && Lesson.lessonPerDay != null){
            System.out.println("todo bien");
        }
        else{
            System.out.println("Algo fallo");
        }
    }
}
