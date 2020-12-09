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

    public int getUserid() {
        return this.userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
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
