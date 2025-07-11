package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public Student create(Student student) {
        return repository.save(student);
    }

    public Student read(long id) {
        return repository.findById(id).orElse(null);
    }

    public Student update(Student student) {
        return repository.save(student);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    public List<Student> findByAge(int age) {
        return repository.findByAge(age);
    }

    public List<Student> findByAgeBetween(int minAge, int maxAge) {
        return repository.findByAgeBetween(minAge, maxAge);
    }

    public Faculty getFacultyByStudentId(long studentId) {
        return repository.findById(studentId)
                .map(Student::getFaculty)
                .orElse(null);
    }

     public Integer countAllStudents() {
        return repository.countAllStudents();
    }

    public Double findAverageAge() {
        return repository.findAverageAge();
    }

    public List<Student> findLastFiveStudents() {
        return repository.findLastFiveStudents();
    }
}