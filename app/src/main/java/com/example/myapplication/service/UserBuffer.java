package com.example.myapplication.service;

import com.example.myapplication.NetworkUtils;
import com.example.myapplication.model.User;

public class UserBuffer extends DataBuffer {
    private int userid;
    private String username;
    private String firstname;
    private String lastname;
    private String email;

    @Override
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String code) {
        this.message = code;
    }

    @Override
    public int get_$StatusCode185() {
        return this._$StatusCode185;
    }

    public void set_$StatusCode185(int _$StatusCode185) {
        this._$StatusCode185 = _$StatusCode185;
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

    @Override
    public void update(Repository repository) {
        repository.updateUser(userid, username, firstname, lastname, email);
        NetworkUtils.MESSAGE = getMessage();
        NetworkUtils.CODE = get_$StatusCode185();
    }
}
