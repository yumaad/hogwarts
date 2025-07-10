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
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class FacultyControllerWebMvcTest {

    private MockMvc mockMvc;

    @Mock
    private FacultyService facultyService;

    @InjectMocks
    private FacultyController facultyController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(facultyController).build();
    }

    @Test
    void createFaculty() throws Exception {
        Faculty faculty = new Faculty("Gryffindor", "Red");
        faculty.setId(1L);

        when(facultyService.create(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(post("/faculties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Gryffindor\",\"color\":\"Red\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Gryffindor"));
    }

    @Test
    void readFaculty() throws Exception {
        Faculty faculty = new Faculty("Slytherin", "Green");
        faculty.setId(1L);

        when(facultyService.read(1L)).thenReturn(faculty);

        mockMvc.perform(get("/faculties/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Slytherin"));
    }

    @Test
    void updateFaculty() throws Exception {
        Faculty faculty = new Faculty("Hufflepuff", "Yellow");
        faculty.setId(1L);

        when(facultyService.update(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(put("/faculties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":\"Hufflepuff\",\"color\":\"Yellow\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.color").value("Yellow"));
    }

    @Test
    void deleteFaculty() throws Exception {
        mockMvc.perform(delete("/faculties/1"))
                .andExpect(status().isOk());
    }

    @Test
    void findByColor() throws Exception {
        Faculty faculty = new Faculty("Ravenclaw", "Blue");

        when(facultyService.findByColor("Blue")).thenReturn(List.of(faculty));

        mockMvc.perform(get("/faculties/color/Blue"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Ravenclaw"));
    }

    @Test
    void findByNameOrColor() throws Exception {
        Faculty faculty = new Faculty("Slytherin", "Green");

        when(facultyService.findByNameOrColor("Slytherin", "Slytherin"))
                .thenReturn(List.of(faculty));

        mockMvc.perform(get("/faculties/search?query=Slytherin"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].color").value("Green"));
    }

    @Test
    void getStudentsByFacultyId() throws Exception {
        mockMvc.perform(get("/faculties/1/students"))
                .andExpect(status().isOk());
    }
}