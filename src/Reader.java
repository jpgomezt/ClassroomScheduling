import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class Reader {

    public static void read(){
        getClassrooms();
        getGroups();
        getStudens();
        ClassRoom.getDistances();
    }

    public static void getClassrooms() {
        try {
            Scanner scanner = new Scanner(new File("/home/salzatec1/Documents/ClassroomScheduling/CSV/aulas.csv"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Scanner lineScanner = new Scanner(line);
                lineScanner.useDelimiter(",");
                String id = lineScanner.next();
                int block = Integer.parseInt(id.substring(0,2));
                String type = "";
                String nextPattern = "";
                if(!lineScanner.hasNextInt()){
                    nextPattern = lineScanner.next();
                    type = type + nextPattern;
                }
                while(!lineScanner.hasNextInt()) {
                    nextPattern = lineScanner.next();
                    if(!nextPattern.equals("N/A")){
                        type = type + "," + nextPattern;
                    }
                }
                int capacity;
                if(nextPattern.equals("N/A")){
                    capacity = -1;
                }
                else {
                    capacity = lineScanner.nextInt();
                }
                boolean access = false;
                if(lineScanner.nextInt() == 1){
                    access = true;
                }
                new ClassRoom(id, type, block, capacity, access);
            }
            scanner.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void getGroups() {
        try {
            Scanner scanner = new Scanner(new File("/home/salzatec1/Documents/ClassroomScheduling/CSV/pa20192.csv"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Scanner lineScanner = new Scanner(line);
                lineScanner.useDelimiter(",");
                String idCourse = lineScanner.next();
                String numberGroup = lineScanner.next();
                int idProffessor = lineScanner.nextInt();
                String Day = lineScanner.next();
                char day = '\0';
                if(Day.equals("miércoles")){
                    day = 'W';
                }
                else {
                    day = Day.toUpperCase().charAt(0);
                }
                String startingTime = lineScanner.next();
                String endingTime = lineScanner.next();
                String initialRoom = lineScanner.next();
                if(!initialRoom.equals("00000")){
                    new Group(idCourse, numberGroup, idProffessor);
                    new Lesson(day, startingTime, endingTime, initialRoom, idCourse+numberGroup);
                }
            }
            scanner.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void getStudens() {
        try {
            Scanner scanner = new Scanner(new File("/home/salzatec1/Documents/ClassroomScheduling/CSV/estudiantes.csv"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Scanner lineScanner = new Scanner(line);
                lineScanner.useDelimiter(",");
                int id = lineScanner.nextInt();
                boolean impairment = false;
                if(lineScanner.nextInt() == 1){
                    impairment = true;
                }
                new Person(id, impairment);
            }
            scanner.close();
            scanner = new Scanner(new File("/home/salzatec1/Documents/ClassroomScheduling/CSV/mat20192.csv"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Scanner lineScanner = new Scanner(line);
                lineScanner.useDelimiter(",");
                int id = lineScanner.nextInt();
                String idCourse = lineScanner.next();
                String numberGroup = lineScanner.next();
                Group group = Group.getGroup(idCourse+numberGroup);
                Person person = Person.getPerson(id);
                if(group != null) {
                    group.addPerson(person);
                    person.addCourses(group);
                }
            }
            scanner.close();
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }
}
