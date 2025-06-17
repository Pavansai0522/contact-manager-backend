package com.example.contactmanager;

import java.util.List;

public class Contact {
    private String firstName;
    private String lastName;
    private String email;
    private String emailStatus;
    private String list;
    private String phone;
    private String contactStatus;
    private List<String> tags;

    public Contact() {}

    public Contact(String firstName, String lastName, String email, String emailStatus, String list, String phone, String contactStatus, List<String> tags) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.emailStatus = emailStatus;
        this.list = list;
        this.phone = phone;
        this.contactStatus = contactStatus;
        this.tags = tags;
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getEmailStatus() { return emailStatus; }
    public void setEmailStatus(String emailStatus) { this.emailStatus = emailStatus; }

    public String getList() { return list; }
    public void setList(String list) { this.list = list; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getContactStatus() { return contactStatus; }
    public void setContactStatus(String contactStatus) { this.contactStatus = contactStatus; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }
}
