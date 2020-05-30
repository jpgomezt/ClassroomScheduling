public class Lesson {
    final char day;
    final String startingTime;
    final String endingTime;
    String classroom;

    public Lesson(char day, String startingTime, String endingTime, String classroom) {
        this.day = day;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
        this.classroom = classroom;
    }
}
