package com.abhaynm.library_management.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class HomeController {
    @GetMapping("/test")
    public ResponseEntity<?>testMethod(){
        return new ResponseEntity<>("testing is successful", HttpStatus.OK);
    }
}
