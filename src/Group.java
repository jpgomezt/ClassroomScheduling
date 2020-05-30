import java.util.ArrayList;
import java.util.HashMap;

public class Group {
    final String id;
    final String number;
    final int idProfessor;
    final ArrayList<Lesson> lessons = new ArrayList<>();
    HashMap<Integer, Person> listStudents = new HashMap<>();

    public Group(String id, String number, int idProfessor, Lesson lesson) {
        this.id = id;
        this.number = number;
        this.idProfessor = idProfessor;
        addLesson(lesson);
    }

    public void addLesson(Lesson lesson){
        this.lessons.add(lesson);
    }

    public void addPerson(Person p){
        listStudents.put(p.getId(), p);
    }

    public String getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public int getIdProfessor() {
        return idProfessor;
    }

    public ArrayList<Lesson> getLessons() {
        return lessons;
    }

    public HashMap<Integer, Person> getListStudents() {
        return listStudents;
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
