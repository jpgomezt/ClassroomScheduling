
import java.util.Map;
import java.util.HashMap;

public class ClassRoom{

    static HashMap<String, ClassRoom> listClassRooms = new HashMap<>();;

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


