package com.example.attendance.controller;

import com.example.attendance.model.Student;

public class StudentController {
    public Student addStudent(String name, String rollNumber) {
        return new Student(name, rollNumber);
    }
}