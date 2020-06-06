import java.util.HashMap;

public class Person {

    // Map that stores all spersons, and can be accesed by the id
    static HashMap<Integer, Person> listPerson = new HashMap<>();

    // The identification of each person
    private final int id;
    // Defines if a this person has an impaiment or not
    private final boolean impairment;
    // A list of all the groups he attends
    private final HashMap<String, Group> groups = new HashMap<>();

    /**
     * Constructor of persons
     * @param id The id of the person
     * @param impairment If this person has impairment or not
     */
    public Person(int id, boolean impairment) {
        this.id = id;
        this.impairment = impairment;
        addPerson(this);
    }

    /**
     * Getter for id of the person
     * @return int
     */
    public int getId() {
        return this.id;
    }

    /**
     * Getter fo the groups this person attends to
     * @return HashMap<String, Group>
     */
    public HashMap<String, Group>  getGroups(){
        return this.groups;
    }

    /**
     * This method add a person to a Map from integer to person
     * @param person Person to add to the list of person
     */
    public static void addPerson(Person person){
        listPerson.put(person.getId(), person);
    }

    /**
     * This method return the person from a map, given an id
     * @param id The id of the person
     * @return Person
     */
    public static Person getPerson(int id){
        return listPerson.get(id);
    }

    /**
     * This method add a group to the map of courses a person attends to
     * @param g add a group to the groups a person attends to
     */
    public void addCourses(Group g) {
        this.groups.put(g.getIdCourse()+":"+g.getNumber(), g);
    }

    /**
     * This method return if a person has mobility impairment or not
     * @return boolean if this person has impairment
     */
    public boolean getMobility() {
        return this.impairment;
    }

    /**
     * This method return a string a describes a person
     */
    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", mobility=" + impairment +
                '}';
    }
}
