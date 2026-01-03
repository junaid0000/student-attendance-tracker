package com.example.attendance.model;

import java.util.Objects;

public class Student {
    private String studentId;
    private String name;
    private String rollNumber;

    // constructor
    public Student(String studentId, String name, String rollNumber) {
        this.studentId = studentId;
        this.name = name;
        this.rollNumber = rollNumber;
    }

    // constructor
    public Student(String name, String rollNumber) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.studentId = java.util.UUID.randomUUID().toString();
    }

    public String getStudentId() {
        return studentId;
    }
    public String getName() {
        return name;
    }
    public String getRollNumber() {
        return rollNumber;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }
        Student other = (Student) obj;
        return Objects.equals(rollNumber, other.rollNumber);
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    @Override
    public int hashCode() {
        if (studentId == null) {
            return 0;
        }
        return studentId.hashCode();
    }
}