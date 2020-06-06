import java.util.HashMap;

public class Group {

    static HashMap<String, Group> listGroups = new HashMap<>();

    private final String idCourse;
    private final String number;
    private final int idProfessor;
    private HashMap<Integer, Person> listStudents = new HashMap<>();

    public Group(String idCourse, String number, int idProfessor) {
        this.idCourse = idCourse;
        this.number = number;
        this.idProfessor = idProfessor;
        addGroup(this);
    }

    public HashMap<Integer, Person> getListStudents() {
        return listStudents;
    }

    public boolean hasPerson(){
        return !this.listStudents.isEmpty();
    }

    public static Group getGroup(String key){
        return listGroups.get(key);
    }

    public static void addGroup(Group group){
        listGroups.put(group.getIdCourse()+group.getNumber(), group);
    }

    public String getIdCourse() {
        return idCourse;
    }

    public String getNumber() {
        return number;
    }

    public void addPerson(Person p){
        listStudents.put(p.getId(), p);
    }

    public boolean hasImpairment(){
        for(Integer key: listStudents.keySet()){
            if(listStudents.get(key).getMobility()){
                return true;
            }
        }
        return false;
    }
}
