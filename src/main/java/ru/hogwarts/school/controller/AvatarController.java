package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.service.AvatarService;
import java.util.List;

@RestController
@RequestMapping("/avatars")
public class AvatarController {
    private final AvatarService service;

    public AvatarController(AvatarService service) {
        this.service = service;
    }

    @GetMapping
    public List<Avatar> getAllAvatars(@RequestParam Integer page, @RequestParam Integer size) {
        return service.getAllAvatars(page, size);
    }
}
