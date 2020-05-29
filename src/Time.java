public class Time {
    final String time;
    final int hour;
    final int minute;

    public Time(String time) {
        this.time = time;
        this.hour = Integer.parseInt(this.time.substring(0,2));
        this.minute = Integer.parseInt(this.time.substring(3));
    }
}
