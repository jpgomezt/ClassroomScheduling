import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class Reader {
    static HashMap<String, ClassRoom> listClassRooms = new HashMap<>();;
    static HashMap<String, Group> listGroups;
    static HashMap<String, Integer> listDistances;
    static HashMap<String, Group>[] lessonPerDay = new HashMap[7];
    

    public static void fillLessons(){
        for(int i=0; i<lessonPerDay.length; i++){
            lessonPerDay[i] = new HashMap<String, Group>();
        }
    }

    public static HashMap<String, ClassRoom> getClassrooms() {
        listClassRooms = new HashMap<>();
        try {
            Scanner scanner = new Scanner(new File("/Users/jpgomezt/Library/Mobile Documents/com~apple~CloudDocs/EAFIT/Tercer Semestre/Algoritmos/ClassroomScheduling/CSV/aulas.csv"));
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
                if(listClassRooms.get(id) != null){
                    System.out.println(id);
                }
                listClassRooms.put(id, new ClassRoom(id, type, block, capacity, access));
            }
            scanner.close();
        }
        catch (Exception e){
            System.err.println();
            return null;
        }
        return listClassRooms;
    }

    public static HashMap<String, Group> getGroups() {
        listGroups = new HashMap<>();
        try {
            Scanner scanner = new Scanner(new File("/Users/jpgomezt/Library/Mobile Documents/com~apple~CloudDocs/EAFIT/Tercer Semestre/Algoritmos/ClassroomScheduling/CSV/pa20192.csv"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Scanner lineScanner = new Scanner(line);
                lineScanner.useDelimiter(",");
                String id = lineScanner.next();
                String number = lineScanner.next();
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
                    Lesson lesson = new Lesson(day, startingTime, endingTime, initialRoom);
                    Group group = new Group(id, number, idProffessor, lesson);
                    if(listGroups.get(id+":"+number) != null){
                        listGroups.get(id+":"+number).addLesson(lesson);
                    }
                    else{
                        listGroups.put(id+":"+number, group);
                    }
                }
            }
            scanner.close();
        }
        catch (Exception e) {
            System.err.println(e);
            return null;
        }
        getStudens();
        cleanGroups();
        return listGroups;
    }

    private static void getStudens() {
        HashMap<Integer, Person> listStudents = new HashMap<>();
        try {
            Scanner scanner = new Scanner(new File("/Users/jpgomezt/Library/Mobile Documents/com~apple~CloudDocs/EAFIT/Tercer Semestre/Algoritmos/ClassroomScheduling/CSV/estudiantes.csv"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Scanner lineScanner = new Scanner(line);
                lineScanner.useDelimiter(",");
                int id = lineScanner.nextInt();
                boolean impairment = false;
                if(lineScanner.nextInt() == 1){
                    impairment = true;
                }
                listStudents.put(id, new Person(id, impairment));
            }
            scanner.close();
            scanner = new Scanner(new File("/Users/jpgomezt/Library/Mobile Documents/com~apple~CloudDocs/EAFIT/Tercer Semestre/Algoritmos/ClassroomScheduling/CSV/mat20192.csv"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Scanner lineScanner = new Scanner(line);
                lineScanner.useDelimiter(",");
                int id = lineScanner.nextInt();
                String course = lineScanner.next();
                String group = lineScanner.next();
                if(listGroups.get(course+":"+group) != null) {
                    listGroups.get(course+":"+group).addPerson(listStudents.get(id));
                    listStudents.get(id).addCourses(listGroups.get(course+":"+group));
                }
            }
            scanner.close();
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }

    private static void cleanGroups() {
        fillLessons();
        Iterator<HashMap.Entry<String,Group>> iter = listGroups.entrySet().iterator();
        while (iter.hasNext()) {
            HashMap.Entry<String,Group> entry = iter.next();
            if(entry.getValue().getListStudents().isEmpty()){
                iter.remove();
            }
            else{
                for (Lesson l:entry.getValue().getLessons()) {
                    switch (l.day) {
                        case 'L' -> lessonPerDay[0].put(entry.getKey(), entry.getValue());
                        case 'M' -> lessonPerDay[1].put(entry.getKey(), entry.getValue());
                        case 'W' -> lessonPerDay[2].put(entry.getKey(), entry.getValue());
                        case 'J' -> lessonPerDay[3].put(entry.getKey(), entry.getValue());
                        case 'V' -> lessonPerDay[4].put(entry.getKey(), entry.getValue());
                        case 'S' -> lessonPerDay[5].put(entry.getKey(), entry.getValue());
                        case 'D' -> lessonPerDay[6].put(entry.getKey(), entry.getValue());
                    }
                }
            }
        }
    }

    public static HashMap<String, Integer> getDistances() {
        listDistances = new HashMap<>();
        try {
            Scanner scanner = new Scanner(new File("/Users/jpgomezt/Library/Mobile Documents/com~apple~CloudDocs/EAFIT/Tercer Semestre/Algoritmos/ClassroomScheduling/CSV/DistanciasBloques.csv"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Scanner lineScanner = new Scanner(line);
                lineScanner.useDelimiter(",");
                int initialBlock = lineScanner.nextInt();
                int finalBlock = lineScanner.nextInt();
                int distance = lineScanner.nextInt();
                if(initialBlock<finalBlock){
                    listDistances.put(Integer.toString(finalBlock) + initialBlock, distance);
                }
                else{
                    listDistances.put(Integer.toString(initialBlock) + finalBlock, distance);
                }
            }
            scanner.close();
        }
        catch (Exception e){
            System.err.println();
            return null;
        }
        return listDistances;
    }

}
