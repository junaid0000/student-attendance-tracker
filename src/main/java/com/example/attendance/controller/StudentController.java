package com.example.attendance.controller;

import java.util.List;
import java.util.Optional;

import com.example.attendance.model.Student;
import com.example.attendance.repository.StudentRepository;

public class StudentController {
    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student addStudent(String name, String rollNumber) {
        Optional<Student> existingStudent = studentRepository.findByRollNumber(rollNumber);
        if (existingStudent.isPresent()) {
            throw new IllegalArgumentException("Student with roll number '" + rollNumber + "' already exists");
        }
        Student student = new Student(name, rollNumber);
        return studentRepository.save(student);
    }

    public Student updateStudent(String rollNumber, String newName, String newRollNumber) {
        Optional<Student> existingStudent = studentRepository.findByRollNumber(rollNumber);
        if (!existingStudent.isPresent()) {
            throw new IllegalArgumentException("Student not found with roll number: " + rollNumber);
        }
        
        Student student = existingStudent.get();
        
        // Check if new roll number already exists
        if (!rollNumber.equals(newRollNumber)) {
            Optional<Student> studentWithNewRoll = studentRepository.findByRollNumber(newRollNumber);
            if (studentWithNewRoll.isPresent()) {
                throw new IllegalArgumentException("Student with roll number '" + newRollNumber + "' already exists");
            }
        }
        
        student.setName(newName);
        student.setRollNumber(newRollNumber);
        // Call update instead of save
        studentRepository.update(student);
        return student;
    }

    public void deleteStudent(String rollNumber) {
        Student student = studentRepository.findByRollNumber(rollNumber).get();
        studentRepository.delete(student);
    }
    public Optional<Student> getStudentByRollNumber(String rollNumber) {
        return studentRepository.findByRollNumber(rollNumber);
    }
}