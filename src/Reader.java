import java.io.File;
import java.util.Scanner;

public class Reader {

    /**
     * This method read all the files necessary for the proyect
     * and clenas unnecesary data
     */
    public static void read(){
        getClassrooms();
        getGroups();
        getStudens();
        Lesson.cleanLesson();
        ClassRoom.getDistances();
    }

    /**
     * This method read the file aulas.csv and recollects all its data
     */
    private static void getClassrooms() {
        try {
            Scanner scanner = new Scanner(new File("aulas.csv"));
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
                lineScanner.close();
            }
            scanner.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * This method read the file pa20292.csv and recollects all its data
     */
    private static void getGroups() {
        try {
            Scanner scanner = new Scanner(new File("pa20192.csv"));
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
                if(initialRoom.equals("00000")){
                    Lesson.irrelevantData.add(new Lesson(day, startingTime, endingTime, initialRoom, idCourse+numberGroup, idProffessor));
                }else{
                    new Group(idCourse, numberGroup);
                    new Lesson(day, startingTime, endingTime, initialRoom, idCourse+numberGroup, idProffessor);
                }
                lineScanner.close();
            }
            scanner.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method read the file estudiantes.csv and recollects all its data
     */
    private static void getStudens() {
        try {
            Scanner scanner = new Scanner(new File("estudiantes.csv"));
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
                lineScanner.close();
            }
            scanner.close();
            scanner = new Scanner(new File("mat20192.csv"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Scanner lineScanner = new Scanner(line);
                lineScanner.useDelimiter(",");
                int id = lineScanner.nextInt();
                String idCourse = lineScanner.next();
                String numberGroup = lineScanner.next();
                if(numberGroup.length()<3){
                    if(numberGroup.length()==1){
                        numberGroup = "00" + numberGroup;
                    }
                    else{
                        numberGroup = "0" + numberGroup;
                    }
                }
                Group group = Group.getGroup(idCourse+numberGroup);
                Person person = Person.getPerson(id);
                if(group != null) {
                    group.addPerson(person);
                    person.addCourses(group);
                }
                lineScanner.close();
            }
            scanner.close();
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }
}
