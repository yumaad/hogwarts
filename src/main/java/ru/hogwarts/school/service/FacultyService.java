package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class FacultyService {
    private static final Logger logger = LoggerFactory.getLogger(FacultyService.class);
    private final FacultyRepository repository;

    public FacultyService(FacultyRepository repository) {
        this.repository = repository;
    }

    public Faculty create(Faculty faculty) {
        logger.info("Creating faculty: {} ({})", faculty.getName(), faculty.getColor());
        return repository.save(faculty);
    }

    public Faculty read(long id) {
        return repository.findById(id).orElse(null);
    }

    public Faculty update(Faculty faculty) {
        return repository.save(faculty);
    }

    public void delete(long id) {
        logger.warn("Deleting faculty ID: {}", id);
        repository.deleteById(id);
    }

    public List<Faculty> findByColor(String color) {
        return repository.findByColor(color);
    }

    public List<Faculty> findByNameOrColor(String name, String color) {
        return repository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    public Set<Student> getStudentsByFacultyId(long facultyId) {
        return repository.findById(facultyId)
                .map(Faculty::getStudents)
                .orElse(Collections.emptySet());
    }
}