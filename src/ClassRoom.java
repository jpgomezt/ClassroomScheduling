
import java.util.Map;
import java.util.HashMap;

public class ClassRoom{

    final String id;

    final String type;

    final int block;

    final int capacity;

    final boolean access;

    HashMap<Character, HashMap<String, Group>> bookings;

    public ClassRoom(String id, String type, int block, int capacity, boolean access) {
        this.id = id;
        this.type = type;
        this.block = block;
        this.capacity = capacity;
        this.access = access;
    }

    public String getType() {
        return type;
    }
}


