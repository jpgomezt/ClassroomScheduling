import java.sql.Time;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        //HashMap<Integer, Person> listStudents = Reader.getStudens();
        String s = "09:00";
        int hour = Integer.parseInt(s.substring(0,2));
        int minute = Integer.parseInt(s.substring(3));
        System.out.println(hour + ":" + minute);
    }
}
