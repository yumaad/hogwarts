package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.stream.LongStream;

@RestController
@RequestMapping("/info")
public class InfoController {

    @Value("${server.port}")
    private String port;

    @GetMapping("/port")
    public String getPort() {
        return "Application is running on port: " + port;
    }

    @GetMapping("/sum")
    public long calculateSum() {
        return LongStream.rangeClosed(1, 1_000_000L)
                .parallel()
                .sum();
    }
}