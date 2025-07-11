package ru.hogwarts.school.repository;

import ru.hogwarts.school.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAge(int age);
    List<Student> findByAgeBetween(int minAge, int maxAge);

    @Query("SELECT COUNT(*) FROM Student")
            Integer countAllStudents();

            @Query("SELECT AVG(age) FROM Student")
            Double findAverageAge();

            @Query("SELECT s FROM Student s ORDER BY s.id DESC LIMIT 5")
            List<Student> findLastFiveStudents();
}