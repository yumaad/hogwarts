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
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/print-parallel")
    public void printStudentsParallel() {
        studentService.printStudentsParallel();
    }

    @GetMapping("/print-synchronized")
    public void printStudentsSynchronized() {
        studentService.printStudentsSynchronized();
    }

    @PostMapping
    public Student create(@RequestBody Student student) {
        return studentService.create(student);
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> read(@PathVariable long id) {
        Student student = studentService.read(id);
        return student != null
                ? ResponseEntity.ok(student)
                : ResponseEntity.notFound().build();
    }

    @PutMapping
    public Student update(@RequestBody Student student) {
        return studentService.update(student);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable long id) {
        studentService.delete(id);
    }

    @GetMapping("/age/{age}")
    public List<Student> findByAge(@PathVariable int age) {
        return studentService.findByAge(age);
    }

    @GetMapping("/age-between")
    public List<Student> findByAgeBetween(
            @RequestParam int minAge,
            @RequestParam int maxAge
    ) {
        return studentService.findByAgeBetween(minAge, maxAge);
    }

    @GetMapping("/faculty/{id}")
    public Faculty getFacultyByStudentId(@PathVariable long id) {
        return studentService.getFacultyByStudentId(id);
    }

    @GetMapping("/count")
    public Integer getStudentsCount() {
        return studentService.countAllStudents();
    }

    @GetMapping("/average-age")
    public Double getAverageAge() {
        return studentService.findAverageAge();
    }

    @GetMapping("/last-five")
    public List<Student> getLastFiveStudents() {
        return studentService.findLastFiveStudents();
    }

    @GetMapping("/names-starting-with-a")
    public List<String> getStudentsNamesStartingWithA() {
        return studentService.getStudentsNamesStartingWithA();
    }

    @GetMapping("/longest-faculty-name")
    public String getLongestFacultyName() {
        return studentService.getLongestFacultyName();
    }
}