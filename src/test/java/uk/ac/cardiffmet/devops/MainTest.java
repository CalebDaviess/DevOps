package uk.ac.cardiffmet.devops;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.Test;

class MainTest {
    @Test
    void mainPrintsWelcomeAndConfiguredTasks() {
        String output = captureOutput(() -> Main.main(new String[0]));

        assertEquals(
                String.join(
                        System.lineSeparator(),
                        "Welcome to the DevOps project!",
                        "Tasks to complete: [make some code changes, get calum  to review PR, "
                                + "Set up CI/CD pipeline, Implement automated testing, celebrate, "
                                + "deploy to production]",
                        ""),
                output);
    }

    @Test
    void printTasksDisplaysProvidedTasks() {
        String output = captureOutput(() -> Main.printTasks(List.of("write tests", "review results")));

        assertEquals("Tasks to complete: [write tests, review results]" + System.lineSeparator(), output);
    }

    @Test
    void printTasksDisplaysEmptyList() {
        String output = captureOutput(() -> Main.printTasks(List.of()));

        assertEquals("Tasks to complete: []" + System.lineSeparator(), output);
    }

    private String captureOutput(Runnable action) {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try (PrintStream capturedOut = new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setOut(capturedOut);
            action.run();
        } finally {
            System.setOut(originalOut);
        }
        return output.toString(StandardCharsets.UTF_8);
    }
}
