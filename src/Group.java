import java.util.HashMap;

public class Group {

    //Map with the list of all groups
    static HashMap<String, Group> listGroups = new HashMap<>();

    //Group id
    private final String idCourse;
    //Group number
    private final String number;
    //Map with the list of students of the group
    private final HashMap<Integer, Person> listStudents = new HashMap<>();

    /**
     * Constructor for Group
     * @param idCourse String
     * @param number String
     */
    public Group(String idCourse, String number) {
        this.idCourse = idCourse;
        this.number = number;
        addGroup(this);
    }

    /**
     * Getter for list of Strudents
     * @return HashMap<Integer, Person>
     */
    public HashMap<Integer, Person> getListStudents() {
        return this.listStudents;
    }

    /**
     * Checks if a Group has persons in it
     * @return boolean
     */
    public boolean hasPerson(){
        return !this.listStudents.isEmpty();
    }

    /**
     * Getter for id
     * @return String
     */
    public String getIdCourse() {
        return this.idCourse;
    }

    /**
     * Getter for number of group
     * @return String
     */
    public String getNumber() {
        return this.number;
    }

    /**
     * Adds a person to the map
     * @param p Person
     */
    public void addPerson(Person p){
        this.listStudents.put(p.getId(), p);
    }

    /**
     * Check if a group has an student with impairment
     * @return boolean
     */
    public boolean hasImpairment(){
        for(Integer key: this.listStudents.keySet()){
            if(this.listStudents.get(key).getMobility()){
                return true;
            }
        }
        return false;
    }

    /**
     * Getter of a group
     * @param key String
     * @return Group
     */
    public static Group getGroup(String key){
        return listGroups.get(key);
    }

    /**
     * Adds a group to the map
     * @param group Group
     */
    public static void addGroup(Group group){
        listGroups.put(group.getIdCourse()+group.getNumber(), group);
    }
}
