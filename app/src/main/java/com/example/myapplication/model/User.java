package com.example.myapplication.model;

public class User {

    private static class InstanceHolder {
        private static final User INSTANCE = new User();
    }

    private int userid;
    private String username;
    private String firstname;
    private String lastname;
    private String email;

    private User() {
        clear();
    }

    public static User getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public int getUserId() {
        return userid;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public void update(int userid, String username, String firstname, String lastname, String email) {
        this.userid = userid;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public void clear() {
        this.userid = -1;
        this.username = "";
        this.firstname = "";
        this.lastname = "";
        this.email = "";
    }
}
