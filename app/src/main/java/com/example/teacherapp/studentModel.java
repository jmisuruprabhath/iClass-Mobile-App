package com.example.teacherapp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class studentModel {

    private String name;
    private String sid;
    private String id;

    public studentModel() {
        this.name = "";
        this.sid = "";
        this.id = " ";
    }

    public studentModel(String name,String sid, String id) {
        this.name = name;
        this.sid = sid;
        this.id = id;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
