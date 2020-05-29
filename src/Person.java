import java.util.ArrayList;

public class Person {
    final int id;
    final int mobility;
    ArrayList<Integer> courses = new ArrayList<>();

    public Person(int id, int mobility) {
        this.id = id;
        this.mobility = mobility;
    }

    public void addCourses(int courseID) {
        courses.add(courseID);
    }

    public int getId() {
        return id;
    }

    public int getMobility() {
        return mobility;
    }

    public ArrayList<Integer> getCourses() {
        return courses;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", mobility=" + mobility +
                '}';
    }
}
