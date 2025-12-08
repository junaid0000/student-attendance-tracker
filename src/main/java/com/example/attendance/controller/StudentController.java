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
 
    public Student updateStudent(String rollNumber, String newName, String newRollNumber) {
        Student student = studentRepository.findByRollNumber(rollNumber).get();
        student.setName(newName);
        student.setRollNumber(newRollNumber);
        return studentRepository.save(student);
    }
    
    public void deleteStudent(String rollNumber) {
        Student student = studentRepository.findByRollNumber(rollNumber).get();
        studentRepository.delete(student);
    }
}