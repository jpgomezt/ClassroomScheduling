import java.util.Stack;
public class Schedule{
    // Stack that contains all lesson to be localized
    private static Stack<Lesson> order;

    /**
     * This method schedule all the lesson in the university
     * trying to improve the distance between to inmediate classes.
     * It priorize the hard restriction which is all lessons with a student with impairments
     * is located in a classrom that can be accesed by those students.
     * then, that the capacity of the clasroom should be ideal for the numbers
     * of person in the group and finally the the distances between al classes
     * is minimized.
     */
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

    /**
     * This method pushes into a stack all the lesson that we need to analyze
     * given a day and a time
     * @param day The day to analyze
     * @param time The time to analyze given the day
     */
    private static void lessonsTime(int day, int time){
        order.clear();
        for (Lesson less : Lesson.lessonPerDay[day].values()){
            if (less.getIntStart() == time && !less.isVisited()){
                order.push(less);
            }
        }
    }


}
