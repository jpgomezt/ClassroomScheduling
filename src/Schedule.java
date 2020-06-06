import java.util.HashMap;
import java.util.Stack;
public class Schedule{
    private static Stack<Lesson> order;

    public static void scheduling(){
        order = new Stack<>();
        for(int i = 0; i <= 6; i++){
            int actualTime = 600;
            System.out.println("Day: "+i);
            while(actualTime <= 2100){
                lessonsTime(i, actualTime);
                while(!order.isEmpty()){
                    Lesson actual = order.pop();
                    System.out.print(actual);
                    actual.setVisit(true);
                    Lesson[] next = actual.nextLessons();
                    for(Lesson nextAnalisis : next){
                        if(!nextAnalisis.isVisited()){
                            order.push(nextAnalisis);
                        }
                    }
                }

                if(actualTime % 100 ==30){
                    actualTime += 70;
                }else{
                    actualTime+= 30;
                }
            }
        }
    }

    public static void lessonsTime(int day, int time){
        order.clear();
        for (Lesson less : Lesson.lessonPerDay[day].values()){
            if (less.getIntStart() == time && !less.isVisited()){
                order.push(less);
            }
        }
    }

    public static void printStack(){
        System.out.print(order.toString().replaceAll("\\[", "").replaceAll("]", ""));
    }


}
