package com.example.contactmanager;

import com.example.contactmanager.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/contacts")
@CrossOrigin(origins = "https://contact-manager-frontend.vercel.app") // Replace with your actual Vercel frontend URL
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    // ✅ GET all contacts
    @GetMapping
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    // ✅ POST create a contact
    @PostMapping
    public Contact createContact(@RequestBody Contact contact) {
        return contactRepository.save(contact);
    }

    // ✅ PUT update a contact
    @PutMapping("/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable String id, @RequestBody Contact updatedContact) {
        return contactRepository.findById(id).map(contact -> {
            contact.setFirstName(updatedContact.getFirstName());
            contact.setLastName(updatedContact.getLastName());
            contact.setEmail(updatedContact.getEmail());
            contact.setEmailStatus(updatedContact.getEmailStatus());
            contact.setList(updatedContact.getList());
            contact.setPhone(updatedContact.getPhone());
            contact.setContactStatus(updatedContact.getContactStatus());
            contact.setTags(updatedContact.getTags());
            return ResponseEntity.ok(contactRepository.save(contact));
        }).orElse(ResponseEntity.notFound().build());
    }

    // ✅ DELETE a contact
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable String id) {
        contactRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

// ✅ IMPORT endpoint at the bottom
    @PostMapping("/import")
    public ResponseEntity<List<Contact>> importContacts() {
        Contact c1 = new Contact("John", "Doe", "john@example.com", "Subscribed", "List A", "1234567890", "New Lead", List.of("tag1", "tag2"));
        Contact c2 = new Contact("Jane", "Smith", "jane@example.com", "Unsubscribed", "List B", "9876543210", "Active", List.of("tag3"));

        List<Contact> imported = List.of(c1, c2);
        contactRepository.saveAll(imported);
        return ResponseEntity.ok(imported);
    }
}