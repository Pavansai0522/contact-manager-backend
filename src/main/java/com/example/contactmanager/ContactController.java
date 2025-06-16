package com.example.contactmanager;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
@CrossOrigin(origins = "@CrossOrigin(origins = \"https://contact-manager-frontend-git-...vercel.app\")\r\n" + //
        "")
@RestController
@RequestMapping("/contacts")
public class ContactController {

    @GetMapping("/")
    public String home() {
        return "✅ Contact Manager Backend is Running!";
    }

    @PostMapping("/import")
    public ResponseEntity<List<Contact>> importContacts() {
        // Simulate importing static data
        Contact c1 = new Contact("John", "Doe", "john@example.com", "Subscribed", "List A", "1234567890", "New Lead", List.of("tag1", "tag2"));
        Contact c2 = new Contact("Jane", "Smith", "jane@example.com", "Unsubscribed", "List B", "9876543210", "Active", List.of("tag3"));

        List<Contact> imported = Arrays.asList(c1, c2);
        return ResponseEntity.ok(imported);
    }
}
