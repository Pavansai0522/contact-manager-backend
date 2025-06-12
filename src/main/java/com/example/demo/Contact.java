package com.example.demo;

public class Contact {
    private String name;
    private String email;
    private String tag;

    public Contact() {}

    public Contact(String name, String email, String tag) {
        this.name = name;
        this.email = email;
        this.tag = tag;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTag() { return tag; }
    public void setTag(String tag) { this.tag = tag; }
}
