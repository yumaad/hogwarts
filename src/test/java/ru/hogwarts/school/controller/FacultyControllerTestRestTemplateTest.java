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
class FacultyControllerTestRestTemplateTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private Faculty createTestFaculty(String name, String color) {
        Faculty faculty = new Faculty(name, color);
        ResponseEntity<Faculty> response = restTemplate.postForEntity(
                "/faculties",
                faculty,
                Faculty.class
        );
        return response.getBody();
    }

    @Test
    void createFaculty() {
        Faculty faculty = new Faculty("Gryffindor", "Red");
        ResponseEntity<Faculty> response = restTemplate.postForEntity(
                "/faculties",
                faculty,
                Faculty.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody().getId());
        assertEquals("Gryffindor", response.getBody().getName());
    }

    @Test
    void readFaculty() {
        Faculty created = createTestFaculty("Slytherin", "Green");
        ResponseEntity<Faculty> response = restTemplate.getForEntity(
                "/faculties/" + created.getId(),
                Faculty.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Slytherin", response.getBody().getName());
    }

    @Test
    void updateFaculty() {
        Faculty created = createTestFaculty("Hufflepuff", "Yellow");
        created.setColor("Gold");

        ResponseEntity<Faculty> response = restTemplate.exchange(
                "/faculties",
                HttpMethod.PUT,
                new HttpEntity<>(created),
                Faculty.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Gold", response.getBody().getColor());
    }

    @Test
    void deleteFaculty() {
        Faculty created = createTestFaculty("Ravenclaw", "Blue");
        restTemplate.delete("/faculties/" + created.getId());

        ResponseEntity<Faculty> response = restTemplate.getForEntity(
                "/faculties/" + created.getId(),
                Faculty.class
        );
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void findByColor() {
        createTestFaculty("Ravenclaw", "Blue");
        ResponseEntity<Faculty[]> response = restTemplate.getForEntity(
                "/faculties/color/Blue",
                Faculty[].class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().length > 0);
    }

    @Test
    void findByNameOrColor() {
        createTestFaculty("Slytherin", "Green");
        ResponseEntity<Faculty[]> response = restTemplate.getForEntity(
                "/faculties/search?query=Green",
                Faculty[].class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().length > 0);
    }

    @Test
    void getStudentsByFacultyId() {
        Faculty created = createTestFaculty("Hufflepuff", "Yellow");
        ResponseEntity<Student[]> response = restTemplate.getForEntity(
                "/faculties/" + created.getId() + "/students",
                Student[].class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}