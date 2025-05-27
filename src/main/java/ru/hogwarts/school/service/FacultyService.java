package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class FacultyService {
    private final FacultyRepository repository;

    public FacultyService(FacultyRepository repository) {
        this.repository = repository;
    }

    public Faculty create(Faculty faculty) {
        return repository.save(faculty);
    }

    public Faculty read(long id) {
        return repository.findById(id).orElse(null);
    }

    public Faculty update(Faculty faculty) {
        return repository.save(faculty);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    public List<Faculty> findByColor(String color) {
        return repository.findByColor(color);
    }

    public List<Faculty> findByNameOrColor(String name, String color) {
        return repository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }
}