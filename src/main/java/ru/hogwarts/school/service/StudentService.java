package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void printStudentsParallel() {
        List<Student> students = studentRepository.findAll();
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

    public void printStudentsSynchronized() {
        List<Student> students = studentRepository.findAll();
        if (students.size() >= 6) {
            printStudentSync(students.get(0));
            printStudentSync(students.get(1));

            new Thread(() -> {
                printStudentSync(students.get(2));
                printStudentSync(students.get(3));
            }).start();

            new Thread(() -> {
                printStudentSync(students.get(4));
                printStudentSync(students.get(5));
            }).start();
        }
    }

    private synchronized void printStudentSync(Student student) {
        System.out.println(student.getName());
    }

    public Student create(Student student) {
        return studentRepository.save(student);
    }

    public Student read(long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student update(Student student) {
        return studentRepository.save(student);
    }

    public void delete(long id) {
        studentRepository.deleteById(id);
    }

    public List<Student> findByAge(int age) {
        return studentRepository.findByAge(age);
    }

    public List<Student> findByAgeBetween(int minAge, int maxAge) {
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    public Integer countAllStudents() {
        return studentRepository.countAllStudents();
    }

    public Double findAverageAge() {
        return studentRepository.findAverageAge();
    }

    public List<Student> findLastFiveStudents() {
        return studentRepository.findLastFiveStudents();
    }

    public List<String> getStudentsNamesStartingWithA() {
        return studentRepository.findAll().parallelStream()
                .map(Student::getName)
                .filter(name -> name.startsWith("A"))
                .map(String::toUpperCase)
                .sorted()
                .collect(Collectors.toList());
    }

    public String getLongestFacultyName() {
        return studentRepository.findAll().parallelStream()
                .map(Student::getFaculty)
                .filter(Objects::nonNull)
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length))
                .orElse("");
    }

    public Faculty getFacultyByStudentId(long studentId) {
        return studentRepository.findById(studentId)
                .map(Student::getFaculty)
                .orElse(null);
    }
}