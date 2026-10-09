package uk.ac.cardiffmet.devops;
import java.util.ArrayList;
import java.util.List;
public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the DevOps project!");
        List<String> tasks = new ArrayList<>();
        tasks.add("make some code changes");
        tasks.add("get calum  to review PR");
        tasks.add("Set up CI/CD pipeline");
        tasks.add("Implement automated testing");
        tasks.add("celebrate");
        System.out.println("Tasks to complete: " + tasks);
    }
    
}