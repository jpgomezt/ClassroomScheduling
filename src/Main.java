import java.io.File;
import java.io.PrintStream;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        Reader.read();
        Schedule.scheduling();
        try{
            PrintStream archSalida = new PrintStream(new File("mat20192new.csv"));
            for (Lesson lesson:Lesson.uselessShit) {
                archSalida.println(lesson);
            }
            for(int i = 0; i <= 6; i++){
                for(Lesson lesson: Lesson.lessonPerDay[i].values()){
                    archSalida.println(lesson);
                }
            }
        }catch (Exception e){
            System.out.print(e);
        }
    }
}
