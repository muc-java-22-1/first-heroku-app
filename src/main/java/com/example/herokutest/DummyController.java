package com.example.herokutest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DummyController {

    @GetMapping
    public String dummy(){
        return "Dies Das Ananas";
    }

}
