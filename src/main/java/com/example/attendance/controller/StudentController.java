package com.example.attendance.controller;

import com.example.attendance.model.Student;
import com.example.attendance.repository.StudentRepository;

public class StudentController {
    private final StudentRepository studentRepository;
    
    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    
    public Student addStudent(String name, String rollNumber) {
        Student student = new Student(name, rollNumber);
        return studentRepository.save(student);
    }
}