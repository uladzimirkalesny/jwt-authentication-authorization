package com.github.uladzimirkalesny.jwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo")
public class HelloWorldController {

    @GetMapping
    public ResponseEntity<String> getHelloWorld() {
        return ResponseEntity.ok("Hello, World!");
    }

}
