import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        HashMap<Integer, Student> listStudents = Reader.getStudens();
        if(listStudents == null){
            System.out.println("Lista vacia");
        }
        else{
            System.out.println("Funciono!");
        }
    }
}
