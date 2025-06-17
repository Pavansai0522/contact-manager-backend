package com.example.contactmanager.repository;

import com.example.contactmanager.models.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContactRepository extends MongoRepository<Contact, String> {
    // You can add custom query methods if needed, e.g.
    // List<Contact> findByEmailStatus(String status);
}
