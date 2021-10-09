package com.example.teacherapp;

public class User {

    String email, mobile, id;

    public User() {

    }

    public User(String email, String mobile, String id) {
        this.email = email;
        this.mobile = mobile;
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
