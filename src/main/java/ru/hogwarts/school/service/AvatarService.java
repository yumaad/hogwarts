package ru.hogwarts.school.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.repository.AvatarRepository;
import java.util.List;

@Service
public class AvatarService {
    private final AvatarRepository repository;

    public AvatarService(AvatarRepository repository) {
        this.repository = repository;
    }

    public List<Avatar> getAllAvatars(Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return repository.findAll(pageRequest).getContent();
    }
}