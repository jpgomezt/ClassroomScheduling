import java.util.Map;
import java.util.HashMap;

public class ClassRoom{

    final int id;

    final String type;

    final int block;

    final int capacity;

    HashMap<char, HashMap<String, Group>> bookings;

    public ClassRoom(int id, String type, int block, boolean access){
        this.id = id;
        this.type = type;
        this.block = block;
        this.capacity = capacity;
        this.bookings = new HashMap<char, new HashMap<String, Group>;

    }
}
