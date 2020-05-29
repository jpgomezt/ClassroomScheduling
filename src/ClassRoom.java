
import java.util.Map;
import java.util.HashMap;

public class ClassRoom{

    final int id;

    final String type;

    final int block;

    final int capacity;

    HashMap<Character, HashMap<String, Group>> bookings;

    public ClassRoom(int id, String type, int block, int capacity) {
        this.id = id;
        this.type = type;
        this.block = block;
        this.capacity = capacity;
    }
}


