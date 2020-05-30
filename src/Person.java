import java.util.ArrayList;
import java.util.HashMap;

public class Person {
    final int id;
    final boolean impairment;
    HashMap<String, Group> groups = new HashMap<>();

    public Person(int id, boolean impairment) {
        this.id = id;
        this.impairment = impairment;
    }

    public void addCourses(Group g) {
        groups.put(g.getId()+":"+g.getNumber(), g);
    }

    public int getId() {
        return id;
    }

    public boolean getMobility() {
        return impairment;
    }


    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", mobility=" + impairment +
                '}';
    }
}
