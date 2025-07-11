package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @PostMapping
    public Student create(@RequestBody Student student) {
        return service.create(student);
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> read(@PathVariable long id) {
        Student student = service.read(id);
        return student != null
                ? ResponseEntity.ok(student)
                : ResponseEntity.notFound().build();
    }

    @PutMapping
    public Student update(@RequestBody Student student) {
        return service.update(student);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }

    @GetMapping("/age/{age}")
    public List<Student> findByAge(@PathVariable int age) {
        return service.findByAge(age);
    }

    @GetMapping("/age-between")
    public List<Student> findByAgeBetween(
            @RequestParam int minAge,
            @RequestParam int maxAge
    ) {
        return service.findByAgeBetween(minAge, maxAge);
    }

    @GetMapping("/faculty/{id}")
    public Faculty getFacultyByStudentId(@PathVariable long id) {
        return service.getFacultyByStudentId(id);
    }

    @GetMapping("/count")
    public Integer getStudentsCount() {
        return service.countAllStudents();
    }

    @GetMapping("/average-age")
    public Double getAverageAge() {
        return service.findAverageAge();
    }

    @GetMapping("/last-five")
    public List<Student> getLastFiveStudents() {
        return service.findLastFiveStudents();
    }
}