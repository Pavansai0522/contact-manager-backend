package com.example.contactmanager;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactController {

    @GetMapping("/")
    public String home() {
        return "✅ Contact Manager Backend is Running!";
    }
}
