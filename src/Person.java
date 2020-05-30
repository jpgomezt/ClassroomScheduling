import java.util.HashMap;

public class Person {

    static HashMap<Integer, Person> listPerson = new HashMap<>();

    private final int id;
    private final boolean impairment;
    private HashMap<String, Group> groups = new HashMap<>();

    public Person(int id, boolean impairment) {
        this.id = id;
        this.impairment = impairment;
        addPerson(this);
    }

    public int getId() {
        return id;
    }

    public static void addPerson(Person person){
        listPerson.put(person.getId(), person);
    }

    public static Person getPerson(int id){
        return listPerson.get(id);
    }

    public void addCourses(Group g) {
        groups.put(g.getIdCourse()+":"+g.getNumber(), g);
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
