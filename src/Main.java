import java.io.File;
import java.io.PrintStream;

/**
 * Main programm of our classroom Scheduling system
 * @author jpgomezt Juan Pablo Gómez
 * @author salzatec1 Santiago Alzate
 */
public class Main {
    public static void main(String[] args) {
        Reader.read();
        Schedule.scheduling();
        try{
            PrintStream archSalida = new PrintStream(new File("pa20192new.csv"));
            for (Lesson lesson:Lesson.irrelevantData) {
                archSalida.println(lesson);
            }
            for(int i = 0; i <= 6; i++){
                for(Lesson lesson: Lesson.lessonPerDay[i].values()){
                    archSalida.println(lesson);
                }
            }
        }catch (Exception e){
            System.out.print("There was a problem writting the file");
        }
    }
}
