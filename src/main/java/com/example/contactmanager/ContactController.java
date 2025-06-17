package com.example.contactmanager;

import com.example.contactmanager.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/contacts")
@CrossOrigin(origins = "https://contact-manager-frontend.vercel.app") // Replace with your actual Vercel frontend URL
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;
       
    @GetMapping("/")
        public String healthCheck() {
            return "✅ Backend is running!";
        }

    // ✅ GET all contacts
        @GetMapping
                public ResponseEntity<?> getAllContacts(
                @RequestParam(defaultValue = "1") int page,
                @RequestParam(defaultValue = "15") int limit,
                @RequestParam(defaultValue = "") String search) {

            Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("firstName").ascending());

            Page<Contact> contactPage;
            if (!search.isEmpty()) {
                contactPage = contactRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
                        search, search, pageable);
            } else {
                contactPage = contactRepository.findAll(pageable);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("contacts", contactPage.getContent());
            response.put("totalPages", contactPage.getTotalPages());
            response.put("totalContacts", contactPage.getTotalElements());

            return ResponseEntity.ok(response);
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