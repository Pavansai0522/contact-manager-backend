package com.example.contactmanager;

import com.example.contactmanager.models.Contact;
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
}
