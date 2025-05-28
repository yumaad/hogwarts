package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/faculties")
public class FacultyController {
    private final FacultyService service;

    public FacultyController(FacultyService service) {
        this.service = service;
    }

    @PostMapping
    public Faculty create(@RequestBody Faculty faculty) {
        return service.create(faculty);
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> read(@PathVariable long id) {
        Faculty faculty = service.read(id);
        return faculty != null
                ? ResponseEntity.ok(faculty)
                : ResponseEntity.notFound().build();
    }

    @PutMapping
    public Faculty update(@RequestBody Faculty faculty) {
        return service.update(faculty);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }

    @GetMapping("/color/{color}")
    public List<Faculty> findByColor(@PathVariable String color) {
        return service.findByColor(color);
    }

    @GetMapping("/search")
    public List<Faculty> findByNameOrColor(
            @RequestParam String query
    ) {
        return service.findByNameOrColor(query, query);
    }

    @GetMapping("/{id}/students")
    public Set<Student> getStudentsByFacultyId(@PathVariable long id) {
        return service.getStudentsByFacultyId(id);
    }
}