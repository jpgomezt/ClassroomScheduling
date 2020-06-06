import java.util.HashMap;
import java.util.Stack;
public class Schedule{
    private static Stack<Lesson> order;

    public static void scheduling(){
        order = new Stack<>();
        for(int i = 0; i <= 6; i++){
            int actualTime = 600;
            while(actualTime <= 2100){
                lessonsTime(i, actualTime);
                while(!order.isEmpty()){
                    Lesson actual = order.pop();
                    Lesson[] next = actual.nextLessons();
                    boolean hasNext = false;
                    for(Lesson nextAnalisis : next){
                        hasNext = true;
                        if(!nextAnalisis.isVisited()){
                            nextAnalisis.addPrevLesson(actual);
                        }
                    }
                    if(ClassRoom.listClassRooms.get(actual.getClassroom()) == null){
                        actual.setNewClassroom(actual.getClassroom());
                        actual.setVisit(true);
                    }
                    else{
                        String type = ClassRoom.listClassRooms.get(actual.getClassroom()).getType();
                        int capacity = Group.listGroups.get(actual.getIdGroup()).getListStudents().size();
                        boolean specialRoom = Group.listGroups.get(actual.getIdGroup()).hasImpairment();
                        if(!actual.hasPrevLesson() && hasNext){
                            actual.setNewClassroom(ClassRoom.getFirstClassroom(type, capacity, specialRoom, actual));
                            actual.setVisit(true);
                        }
                        else if (actual.hasPrevLesson()) {
                            actual.setNewClassroom(ClassRoom.getClassroom(type, capacity, specialRoom, actual));
                            actual.setVisit(true);
                        }
                    }
                }
                if(actualTime % 100 ==30){
                    actualTime += 70;
                }else{
                    actualTime+= 30;
                }
            }
            actualTime = 600;
            while(actualTime <= 2100){
                lessonsTime(i, actualTime);
                while(!order.isEmpty()){
                    Lesson actual = order.pop();
                    String type = ClassRoom.listClassRooms.get(actual.getClassroom()).getType();
                    int capacity = Group.listGroups.get(actual.getIdGroup()).getListStudents().size();
                    boolean specialRoom = Group.listGroups.get(actual.getIdGroup()).hasImpairment();
                    actual.setNewClassroom(ClassRoom.getFirstClassroom(type, capacity, specialRoom, actual));
                    actual.setVisit(true);
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
