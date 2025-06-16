package com.example.contactmanager;

import java.util.List;

public class Contact {
    public String firstName;
    public String lastName;
    public String email;
    public String emailStatus;
    public String list;
    public String phone;
    public String contactStatus;
    public List<String> tags;

    public Contact() {}

    public Contact(String firstName, String lastName, String email, String emailStatus,
                   String list, String phone, String contactStatus, List<String> tags) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.emailStatus = emailStatus;
        this.list = list;
        this.phone = phone;
        this.contactStatus = contactStatus;
        this.tags = tags;
    }
}
