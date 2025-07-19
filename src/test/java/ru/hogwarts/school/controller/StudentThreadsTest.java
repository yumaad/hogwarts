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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class StudentThreadsTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @Test
    void printParallelEndpoint_ShouldCallService() {
        studentController.printStudentsParallel();

        verify(studentService, times(1)).printStudentsParallel();
    }

    @Test
    void printSynchronizedEndpoint_ShouldCallService() {
        studentController.printStudentsSynchronized();

        verify(studentService, times(1)).printStudentsSynchronized();
    }

    @Test
    void printParallel_ShouldPrintInThreads() throws InterruptedException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        studentService.printStudentsParallel();
        Thread.sleep(500);

        String output = outContent.toString();
        assertTrue(output.contains("Harry") || output.contains("Hermione") || output.contains("Ron"));
    }

    @Test
    void printSynchronized_ShouldPrintWithSync() throws InterruptedException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        studentService.printStudentsSynchronized();
        Thread.sleep(500);

        String output = outContent.toString();
        assertTrue(output.contains("Harry") || output.contains("Hermione") || output.contains("Ron"));
    }
}