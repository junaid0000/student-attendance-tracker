package com.example.attendance.controller;

import java.util.Optional;

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
        // Find the student to update
        Student student = studentRepository.findByRollNumber(rollNumber)
            .orElseThrow(() -> new IllegalArgumentException("Student not found"));
     
        student.setName(newName);
        student.setRollNumber(newRollNumber);
        
        // Save updated student
        return studentRepository.save(student);
    }
    
    public boolean deleteStudent(String rollNumber) {
        Optional<Student> student = studentRepository.findByRollNumber(rollNumber);
        if (student.isPresent()) {
            studentRepository.delete(student.get());
            return true;
        }
        return false;
    }
}