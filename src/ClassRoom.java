import javax.sound.midi.Soundbank;
import java.io.File;
import java.util.ArrayList;
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
    private HashMap<Character, ArrayList<String>> bookings = new HashMap<>();

    public ClassRoom(String id, String type, int block, int capacity, boolean access) {
        this.id = id;
        this.type = type;
        this.block = block;
        this.capacity = capacity;
        this.access = access;
        addClass(this);
    }

    public int getBlock(){
        return this.block;
    }

    public void setBookings(char day,int startTime, int endTime) {
        if(bookings.containsKey(day)){
            String str = startTime + ":" + endTime;
            bookings.get(day).add(str);
        }
        else {
            ArrayList<String> times = new ArrayList<>();
            times.add(startTime + ":" + endTime);
            bookings.put(day, times);
        }
    }

    public boolean isBooked(char day, int startTime, int endTime) {
        if(bookings.get(day) == null){return false;}
        for (String str:bookings.get(day)) {
            Scanner scanner = new Scanner(str);
            scanner.useDelimiter(":");
            int start = scanner.nextInt();
            int end = scanner.nextInt();
            if((startTime > start && startTime < end) || (endTime > start && endTime < end)) {
                return true;
            }
        }
        return false;
    }

    public static void getDistances() {
        listDistances = new HashMap<>();
        try {
            Scanner scanner = new Scanner(new File("DistanciasBloques.csv"));
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
            e.printStackTrace();
        }
    }

    public static ClassRoom bestClassRoom(int capacity, boolean specialRoom, Lesson initLesson, String[] typesRequired){
        return null;
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

    public static String getFirstClassroom(String type, int numPersons, boolean specialRoom, Lesson lesson){
        String idCurrentHardRestriction = "";
        int distanceToCapacityHardRestriction = Integer.MAX_VALUE;
        int capacityHardRestriction = Integer.MAX_VALUE;
        String idCurrentSoftRestriction = "";
        int distanceToCapacitySoftRestriction = Integer.MAX_VALUE;
        int capacitySoftRestriction = Integer.MAX_VALUE;
        for(ClassRoom cr: listClassRooms.values()){
            if(!cr.isBooked(lesson.getCharDay(), lesson.getIntStart(), lesson.getIntEnd())){
                if((Math.abs(cr.capacity - numPersons) < distanceToCapacityHardRestriction) && (type.equals(cr.getType())) && (!specialRoom || cr.access)){
                    idCurrentHardRestriction = cr.getId();
                    distanceToCapacityHardRestriction = Math.abs(cr.capacity - numPersons);
                    capacityHardRestriction = cr.capacity;
                }
                if((Math.abs(cr.capacity - numPersons) < distanceToCapacitySoftRestriction) && (type.equals(cr.getType()))){
                    idCurrentSoftRestriction = cr.getId();
                    distanceToCapacitySoftRestriction = Math.abs(cr.capacity - numPersons);
                    capacitySoftRestriction = cr.capacity;
                }
            }
        }
        if(idCurrentHardRestriction.length()==0){
            ClassRoom.listClassRooms.get(idCurrentSoftRestriction).setBookings(lesson.getCharDay(), lesson.getIntStart(), lesson.getIntEnd());
            return idCurrentSoftRestriction;
        }
        else {
            ClassRoom.listClassRooms.get(idCurrentHardRestriction).setBookings(lesson.getCharDay(), lesson.getIntStart(), lesson.getIntEnd());
            return idCurrentHardRestriction;
        }
    }

    public static String getClassroom(String type, int numPersons, boolean specialRoom, Lesson lesson){
        String idCurrentHardRestriction = "";
        int distanceToCapacityHardRestriction = Integer.MAX_VALUE;
        int capacityHardRestriction = Integer.MAX_VALUE;
        int distanceRecorridaHardRestriction = Integer.MAX_VALUE;
        String idCurrentSoftRestriction = "";
        int distanceToCapacitySoftRestriction = Integer.MAX_VALUE;
        int capacitySoftRestriction = Integer.MAX_VALUE;
        int distanceRecorridaSoftRestriction = Integer.MAX_VALUE;
        boolean goodAlocatedSoft = false;
        boolean goodAlocatedHard = false;
        for(ClassRoom cr: listClassRooms.values()){
            int newDistanceRecorrida = lesson.distanceRecorrida(cr);
            if(!cr.isBooked(lesson.getCharDay(), lesson.getIntStart(), lesson.getIntEnd())){
                if(newDistanceRecorrida < distanceRecorridaHardRestriction){
                    if(cr.capacity >= numPersons && type.equals(cr.getType()) && (!specialRoom || cr.access)  && !goodAlocatedHard){
                        idCurrentHardRestriction = cr.getId();
                        distanceToCapacityHardRestriction = Math.abs(cr.capacity - numPersons);
                        capacityHardRestriction = cr.capacity;
                        distanceRecorridaHardRestriction = newDistanceRecorrida;
                        goodAlocatedHard = true;
                    }
                    if(cr.capacity >= numPersons && type.equals(cr.getType()) && (!specialRoom || cr.access)  && goodAlocatedHard){
                        if(cr.capacity < capacityHardRestriction ){
                            idCurrentHardRestriction = cr.getId();
                            distanceToCapacityHardRestriction = Math.abs(cr.capacity - numPersons);
                            capacityHardRestriction = cr.capacity;
                            distanceRecorridaHardRestriction = newDistanceRecorrida;
                        }
                    }
                    if((Math.abs(cr.capacity - numPersons) < distanceToCapacityHardRestriction) && (type.equals(cr.getType())) && (!specialRoom || cr.access) && !goodAlocatedHard){
                        idCurrentHardRestriction = cr.getId();
                        distanceToCapacityHardRestriction = Math.abs(cr.capacity - numPersons);
                        capacityHardRestriction = cr.capacity;
                        distanceRecorridaHardRestriction = newDistanceRecorrida;
                    }
                }
                if(newDistanceRecorrida < distanceRecorridaSoftRestriction){
                    if(cr.capacity >= numPersons && type.equals(cr.getType()) && (!specialRoom || cr.access)  && !goodAlocatedSoft){
                        idCurrentSoftRestriction = cr.getId();
                        distanceToCapacitySoftRestriction = Math.abs(cr.capacity - numPersons);
                        capacitySoftRestriction = cr.capacity;
                        distanceRecorridaSoftRestriction = newDistanceRecorrida;
                        goodAlocatedSoft = true;
                    }
                    if(cr.capacity >= numPersons && type.equals(cr.getType()) && (!specialRoom || cr.access)  && goodAlocatedSoft){
                        if(cr.capacity < capacitySoftRestriction ){
                            idCurrentSoftRestriction = cr.getId();
                            distanceToCapacitySoftRestriction = Math.abs(cr.capacity - numPersons);
                            capacitySoftRestriction = cr.capacity;
                            distanceRecorridaSoftRestriction = newDistanceRecorrida;
                        }
                    }
                    if((Math.abs(cr.capacity - numPersons) < distanceToCapacitySoftRestriction) && (type.equals(cr.getType())) && !goodAlocatedSoft){
                        idCurrentSoftRestriction = cr.getId();
                        distanceToCapacitySoftRestriction = Math.abs(cr.capacity - numPersons);
                        capacitySoftRestriction = cr.capacity;
                        distanceRecorridaSoftRestriction = newDistanceRecorrida;
                    }
                }
            }
        }
        if(idCurrentHardRestriction.length()==0){
            ClassRoom.listClassRooms.get(idCurrentSoftRestriction).setBookings(lesson.getCharDay(), lesson.getIntStart(), lesson.getIntEnd());
            return idCurrentSoftRestriction;
        }
        else {
            ClassRoom.listClassRooms.get(idCurrentHardRestriction).setBookings(lesson.getCharDay(), lesson.getIntStart(), lesson.getIntEnd());
            return idCurrentHardRestriction;
        }
    }
}


