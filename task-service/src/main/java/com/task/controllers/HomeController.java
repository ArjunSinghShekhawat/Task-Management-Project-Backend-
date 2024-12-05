package com.task.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HomeController {

    @GetMapping("/task")
    public ResponseEntity<String> getAssignUserTask() {

        return new ResponseEntity<>("Welcome to task service", HttpStatus.OK);
    }
}
