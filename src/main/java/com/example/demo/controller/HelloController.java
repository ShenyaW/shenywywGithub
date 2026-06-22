package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public Map<String, Object> hello() {
        Map<String, Object> body = new LinkedHashMap<String, Object>();
        body.put("message", "Hello Spring Boot!");
        body.put("time", LocalDateTime.now().toString());
        return body;
    }
}
