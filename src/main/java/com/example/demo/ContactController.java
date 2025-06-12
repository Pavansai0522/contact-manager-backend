package com.example.demo;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    @PostMapping("/import")
    public ResponseEntity<List<Contact>> importContacts() {
        List<Contact> contacts = List.of(
            new Contact("John Doe", "john@example.com", "Friend"),
            new Contact("Jane Smith", "jane@example.com", "Work")
        );
        return ResponseEntity.ok(contacts);
    }
}
