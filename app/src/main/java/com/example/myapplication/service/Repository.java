package com.example.myapplication.service;

import com.example.myapplication.model.User;
import com.example.myapplication.model.Waitlist;
import com.google.gson.annotations.SerializedName;

public class Repository {

    private int userid;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String message;
    @SerializedName("code")
    private int _$StatusCode185;
    private int waitId;
    private String waitCategory;
    private int waitRank;

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

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String code) {
        this.message = code;
    }

    public int get_$StatusCode185() {
        return this._$StatusCode185;
    }

    public void set_$StatusCode185(int _$StatusCode185) {
        this._$StatusCode185 = _$StatusCode185;
    }

    public int getWaitId() {
        return this.waitId;
    }

    public void setWaitId(int waitId) {
        this.waitId = waitId;
    }

    public String getWaitCategory() {
        return this.waitCategory;
    }

    public void setWaitCategory(String waitCategory) {
        this.waitCategory = waitCategory;
    }

    public int getWaitRank() {
        return this.waitRank;
    }

    public void setWaitRank(int waitRank) {
        this.waitRank = waitRank;
    }

    public void loadUser() {
        User user = User.getInstance();
        user.setUserid(userid);
        user.setUsername(username);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setEmail(email);
    }

    public void loadWaitlist() {
        Waitlist wl = Waitlist.getInstance();
        wl.setId(waitId);
        wl.setCategory(waitCategory);
        wl.setRank(waitRank);
    }

    public String show() {
        return waitCategory + waitId + "/" + waitRank;
    }
}
