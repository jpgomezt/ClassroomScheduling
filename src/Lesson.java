public class Lesson {
    final char day;
    final String startingTime;
    final String endingTime;
    ClassRoom classroom;

    public Lesson(char day, String startingTime, String endingTime, ClassRoom classroom) {
        this.day = day;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
        this.classroom = classroom;
    }
}
