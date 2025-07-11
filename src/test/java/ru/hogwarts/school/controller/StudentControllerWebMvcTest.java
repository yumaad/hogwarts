package ru.hogwarts.school.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class StudentControllerWebMvcTest {

    private MockMvc mockMvc;

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }

    @Test
    void createStudent() throws Exception {
        Student student = new Student("Harry Potter", 11);
        student.setId(1L);

        when(studentService.create(any(Student.class))).thenReturn(student);

        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Harry Potter\",\"age\":11}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Harry Potter"));
    }

    @Test
    void readStudent() throws Exception {
        Student student = new Student("Hermione Granger", 12);
        student.setId(1L);

        when(studentService.read(1L)).thenReturn(student);

        mockMvc.perform(get("/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.age").value(12));
    }

    @Test
    void updateStudent() throws Exception {
        Student student = new Student("Ron Weasley", 12);
        student.setId(1L);

        when(studentService.update(any(Student.class))).thenReturn(student);

        mockMvc.perform(put("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":\"Ron Weasley\",\"age\":12}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ron Weasley"));
    }

    @Test
    void deleteStudent() throws Exception {
        mockMvc.perform(delete("/students/1"))
                .andExpect(status().isOk());
    }

    @Test
    void findByAge() throws Exception {
        Student student = new Student("Draco Malfoy", 11);

        when(studentService.findByAge(11)).thenReturn(List.of(student));

        mockMvc.perform(get("/students/age/11"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Draco Malfoy"));
    }

    @Test
    void findByAgeBetween() throws Exception {
        Student student1 = new Student("Ginny Weasley", 10);
        Student student2 = new Student("Luna Lovegood", 11);

        when(studentService.findByAgeBetween(10, 12))
                .thenReturn(List.of(student1, student2));

        mockMvc.perform(get("/students/age-between?minAge=10&maxAge=12"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getFacultyByStudentId() throws Exception {
        Faculty faculty = new Faculty("Gryffindor", "Red");

        when(studentService.getFacultyByStudentId(1L)).thenReturn(faculty);

        mockMvc.perform(get("/students/faculty/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Gryffindor"));
    }
}