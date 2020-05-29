import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class Reader {
    public static HashMap<Integer, Student> getStudens(){
        HashMap<Integer, Student> listStudents = new HashMap<>();
        try {
            Scanner scanner = new Scanner(new File("CSV/estudiantes.csv"));
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                Scanner lineScanner = new Scanner(line);
                lineScanner.useDelimiter(",");
                int id = lineScanner.nextInt();
                int mobility = lineScanner.nextInt();
                listStudents.put(id, new Student(id, mobility));
            }
            scanner.close();
            /**
            scanner = new Scanner(new File("CSV/mat20192.csv"));
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                Scanner lineScanner = new Scanner(line);
                lineScanner.useDelimiter(",");
                int id = lineScanner.nextInt();
                int course = lineScanner.nextInt();
                listStudents.get(id).addCourses(course);
            }
            scanner.close();
             */
        }
        catch (Exception e){
            System.err.println(e);
            return null;
        }
        return listStudents;
    }
}
