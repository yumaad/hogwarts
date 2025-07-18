package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController controller;

    @Test
    void printParallel_ShouldPrintNames() throws InterruptedException {
        List<Student> students = Arrays.asList(
                new Student("Harry", 11),
                new Student("Hermione", 12),
                new Student("Ron", 11),
                new Student("Draco", 11),
                new Student("Luna", 10),
                new Student("Neville", 11)
        );

        when(studentService.getAllStudents()).thenReturn(students);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        controller.printStudentsParallel();
        Thread.sleep(200);

        String output = outContent.toString();
        assertTrue(output.contains("Harry"));
        assertTrue(output.contains("Hermione"));
        assertTrue(output.contains("Ron"));
    }

    @Test
    void printSynchronized_ShouldPrintNames() throws InterruptedException {
        List<Student> students = Arrays.asList(
                new Student("Harry", 11),
                new Student("Hermione", 12),
                new Student("Ron", 11),
                new Student("Draco", 11),
                new Student("Luna", 10),
                new Student("Neville", 11)
        );

        when(studentService.getAllStudents()).thenReturn(students);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        controller.printStudentsSynchronized();
        Thread.sleep(200);

        String output = outContent.toString();
        assertTrue(output.contains("Harry"));
        assertTrue(output.contains("Hermione"));
        assertTrue(output.contains("Ron"));
    }
}