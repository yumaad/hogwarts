package ru.hogwarts.school.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class StudentControllerTestRestTemplateTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private Student createTestStudent(String name, int age) {
        Student student = new Student(name, age);
        ResponseEntity<Student> response = restTemplate.postForEntity(
                "/students",
                student,
                Student.class
        );
        return response.getBody();
    }

    @Test
    void createStudent() {
        Student student = new Student("Harry Potter", 11);
        ResponseEntity<Student> response = restTemplate.postForEntity(
                "/students",
                student,
                Student.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody().getId());
        assertEquals("Harry Potter", response.getBody().getName());
    }

    @Test
    void readStudent() {
        Student created = createTestStudent("Hermione Granger", 12);
        ResponseEntity<Student> response = restTemplate.getForEntity(
                "/students/" + created.getId(),
                Student.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Hermione Granger", response.getBody().getName());
    }

    @Test
    void updateStudent() {
        Student created = createTestStudent("Ron Weasley", 11);
        created.setAge(12);

        ResponseEntity<Student> response = restTemplate.exchange(
                "/students",
                HttpMethod.PUT,
                new HttpEntity<>(created),
                Student.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(12, response.getBody().getAge());
    }

    @Test
    void deleteStudent() {
        Student created = createTestStudent("Neville Longbottom", 11);
        restTemplate.delete("/students/" + created.getId());

        ResponseEntity<Student> response = restTemplate.getForEntity(
                "/students/" + created.getId(),
                Student.class
        );
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void findByAge() {
        createTestStudent("Draco Malfoy", 11);
        ResponseEntity<Student[]> response = restTemplate.getForEntity(
                "/students/age/11",
                Student[].class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().length > 0);
    }

    @Test
    void findByAgeBetween() {
        createTestStudent("Luna Lovegood", 11);
        createTestStudent("Ginny Weasley", 10);

        ResponseEntity<Student[]> response = restTemplate.getForEntity(
                "/students/age-between?minAge=10&maxAge=12",
                Student[].class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().length >= 2);
    }

    @Test
    void getFacultyByStudentId() {
        Student created = createTestStudent("Cedric Diggory", 14);
        ResponseEntity<Faculty> response = restTemplate.getForEntity(
                "/students/faculty/" + created.getId(),
                Faculty.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}