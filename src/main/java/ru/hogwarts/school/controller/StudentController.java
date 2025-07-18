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

    @GetMapping("/print-parallel")
    public void printStudentsParallel() {
        List<Student> students = service.getAllStudents();
        if (students.size() >= 6) {
            System.out.println(students.get(0).getName());
            System.out.println(students.get(1).getName());

            new Thread(() -> {
                System.out.println(students.get(2).getName());
                System.out.println(students.get(3).getName());
            }).start();

            new Thread(() -> {
                System.out.println(students.get(4).getName());
                System.out.println(students.get(5).getName());
            }).start();
        }
    }

    @GetMapping("/print-synchronized")
    public void printStudentsSynchronized() {
        List<Student> students = service.getAllStudents();
        if (students.size() >= 6) {
            printStudentName(students.get(0));
            printStudentName(students.get(1));

            new Thread(() -> {
                printStudentName(students.get(2));
                printStudentName(students.get(3));
            }).start();

            new Thread(() -> {
                printStudentName(students.get(4));
                printStudentName(students.get(5));
            }).start();
        }
    }

    private synchronized void printStudentName(Student student) {
        System.out.println(student.getName());
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

    @GetMapping("/names-starting-with-a")
    public List<String> getStudentsNamesStartingWithA() {
        return service.getStudentsNamesStartingWithA();
    }

    @GetMapping("/longest-faculty-name")
    public String getLongestFacultyName() {
        return service.getLongestFacultyName();
    }
}