import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ClassRoom{

    //map with the list of classrooms
    static HashMap<String, ClassRoom> listClassRooms = new HashMap<>();
    //map with the lis of distances
    static HashMap<String, Integer> listDistances;

    //Classrooom id
    private final String id;
    //Classroom type
    private final String type;
    //Block location of classroom
    private final int block;
    //Classroom capacity
    private final int capacity;
    //If counts with acces for everyone or not
    private final boolean access;
    //map with the bookings of the classroom each day
    private final HashMap<Character, ArrayList<String>> bookings = new HashMap<>();

    /**
     * Constructor of ClassRoom
     * @param id String
     * @param type String
     * @param block int
     * @param capacity int
     * @param access boolean
     */
    public ClassRoom(String id, String type, int block, int capacity, boolean access) {
        this.id = id;
        this.type = type;
        this.block = block;
        this.capacity = capacity;
        this.access = access;
        addClass(this);
    }

    /**
     * Getter for block
     * @return int
     */
    public int getBlock(){
        return this.block;
    }

    /**
     * Getter for id
     * @return String
     */
    public String getId() {
        return this.id;
    }

    /**
     * Getter for type
     * @return String
     */
    public String getType() {
        return this.type;
    }

    /**
     * Sets a new booking
     * @param day char
     * @param startTime int
     * @param endTime int
     */
    public void setBookings(char day,int startTime, int endTime) {
        if(this.bookings.containsKey(day)){
            String str = startTime + ":" + endTime;
            this.bookings.get(day).add(str);
        }
        else {
            ArrayList<String> times = new ArrayList<>();
            times.add(startTime + ":" + endTime);
            this.bookings.put(day, times);
        }
    }

    /**
     * Checks if a room is booked
     * @param day char
     * @param startTime int
     * @param endTime int
     * @return boolean
     */
    public boolean isBooked(char day, int startTime, int endTime) {
        if(this.bookings.get(day) == null){return false;}
        for (String str:this.bookings.get(day)) {
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

    /**
     * Fills the map of distances
     */
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

    /**
     * Method to add a class to the map
     * @param classroom ClassRoom
     */
    public static void addClass(ClassRoom classroom){
        listClassRooms.put(classroom.getId(), classroom);
    }

    /**
     * Get best classroom availables for classes that doesnt deppend on others
     * @param type String
     * @param numPersons int
     * @param specialRoom boolean
     * @param lesson Lesson
     * @return String
     */
    public static String getFirstClassroom(String type, int numPersons, boolean specialRoom, Lesson lesson){
        String idCurrentHardRestriction = "";
        int distanceToCapacityHardRestriction = Integer.MAX_VALUE;
        int capacityHardRestriction = Integer.MAX_VALUE;
        String idCurrentSoftRestriction = "";
        int distanceToCapacitySoftRestriction = Integer.MAX_VALUE;
        int capacitySoftRestriction = Integer.MAX_VALUE;
        boolean goodAlocatedSoft = false;
        boolean goodAlocatedHard = false;
        for(ClassRoom cr: listClassRooms.values()){
            if(!cr.isBooked(lesson.getCharDay(), lesson.getIntStart(), lesson.getIntEnd())){
                if(cr.capacity >= numPersons && type.equals(cr.getType()) && (!specialRoom || cr.access)  && !goodAlocatedHard){
                    idCurrentHardRestriction = cr.getId();
                    distanceToCapacityHardRestriction = Math.abs(cr.capacity - numPersons);
                    capacityHardRestriction = cr.capacity;
                    goodAlocatedHard = true;
                }
                if(cr.capacity >= numPersons && type.equals(cr.getType()) && (!specialRoom || cr.access)  && goodAlocatedHard){
                    if(cr.capacity < capacityHardRestriction ){
                        idCurrentHardRestriction = cr.getId();
                        distanceToCapacityHardRestriction = Math.abs(cr.capacity - numPersons);
                        capacityHardRestriction = cr.capacity;
                    }
                }
                if((Math.abs(cr.capacity - numPersons) < distanceToCapacityHardRestriction) && (type.equals(cr.getType())) && (!specialRoom || cr.access) && !goodAlocatedHard){
                    idCurrentHardRestriction = cr.getId();
                    distanceToCapacityHardRestriction = Math.abs(cr.capacity - numPersons);
                    capacityHardRestriction = cr.capacity;
                }
                if(cr.capacity >= numPersons && type.equals(cr.getType()) && !goodAlocatedSoft){
                    idCurrentSoftRestriction = cr.getId();
                    distanceToCapacitySoftRestriction = Math.abs(cr.capacity - numPersons);
                    capacitySoftRestriction = cr.capacity;
                    goodAlocatedSoft = true;
                }
                if(cr.capacity >= numPersons && type.equals(cr.getType()) && goodAlocatedSoft){
                    if(cr.capacity < capacitySoftRestriction ){
                        idCurrentSoftRestriction = cr.getId();
                        distanceToCapacitySoftRestriction = Math.abs(cr.capacity - numPersons);
                        capacitySoftRestriction = cr.capacity;
                    }
                }
                if((Math.abs(cr.capacity - numPersons) < distanceToCapacitySoftRestriction) && (type.equals(cr.getType())) && !goodAlocatedSoft){
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

    /**
     * Get best classroom availables for classes that deppend on others
     * @param type String
     * @param numPersons int
     * @param specialRoom boolean
     * @param lesson Lesson
     * @return String
     */
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
                    if(cr.capacity >= numPersons && type.equals(cr.getType()) && !goodAlocatedSoft){
                        idCurrentSoftRestriction = cr.getId();
                        distanceToCapacitySoftRestriction = Math.abs(cr.capacity - numPersons);
                        capacitySoftRestriction = cr.capacity;
                        distanceRecorridaSoftRestriction = newDistanceRecorrida;
                        goodAlocatedSoft = true;
                    }
                    if(cr.capacity >= numPersons && type.equals(cr.getType()) && goodAlocatedSoft){
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


