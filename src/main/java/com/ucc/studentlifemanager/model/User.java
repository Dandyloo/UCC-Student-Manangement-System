package com.ucc.studentlifemanager.model;

public class User {
    private int id;
    private String fullName;
    private String studentId;
    private String password;

    public User(String fullName, String studentId, String password) {
        this.fullName = fullName;
        this.studentId = studentId;
        this.password = password;
    }

    public User(int id, String fullName, String studentId, String password) {
        this.id = id;
        this.fullName = fullName;
        this.studentId = studentId;
        this.password = password;
    }

    public int getId() { return id; }
    public String getFullName() { return fullName; }
    public String getStudentId() { return studentId; }
    public String getPassword() { return password; }
}