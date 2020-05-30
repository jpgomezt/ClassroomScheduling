public class Lesson {
    final char day;
    final String startingTime;
    final String endingTime;
    ClassRoom class;

    public Lesson(char day, String startingTime, String endingTimei, ClassRoom class) {
        this.day = day;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
        this.class = class;
    }
}
