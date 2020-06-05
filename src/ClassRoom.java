import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class ClassRoom{

    static HashMap<String, ClassRoom> listClassRooms = new HashMap<>();
    static HashMap<String, Integer> listDistances;

    private final String id;
    private final String type;
    private final int block;
    private final int capacity;
    private final boolean access;
    private HashMap<Character, HashMap<String, Group>> bookings;

    public ClassRoom(String id, String type, int block, int capacity, boolean access) {
        this.id = id;
        this.type = type;
        this.block = block;
        this.capacity = capacity;
        this.access = access;
        addClass(this);
    }

    public static void getDistances() {
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
        }
    }

    public static ClassRoom bestClassRoom(int capacity, boolean specialRoom, Lesson initLesson, String[] typesRequired){
        if()
    }

    public static int bestBlock(){

    }

    public static void addClass(ClassRoom classroom){
        listClassRooms.put(classroom.getId(), classroom);
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}


