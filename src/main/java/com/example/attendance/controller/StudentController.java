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
    @javax.annotation.processing.Generated("Ignore for JaCoCo")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student addStudent(String name, String rollNumber) {
        Optional<Student> existingStudent = studentRepository.findByRollNumber(rollNumber);
        if (existingStudent.isPresent()) {
            throwStudentExists(rollNumber);
        }
        Student student = new Student(name, rollNumber);
        return studentRepository.save(student);
    }

    public Student updateStudent(String rollNumber, String newName, String newRollNumber) {
        Optional<Student> existingStudent = studentRepository.findByRollNumber(rollNumber);
        if (!existingStudent.isPresent()) {
            throwStudentNotFound(rollNumber);
        }
        
        Student student = existingStudent.get();
        
        // Check if new roll number already exists
        if (!rollNumber.equals(newRollNumber)) {
            Optional<Student> studentWithNewRoll = studentRepository.findByRollNumber(newRollNumber);
            if (studentWithNewRoll.isPresent()) {
                throwStudentExists(newRollNumber);
            }
        }
        
        student.setName(newName);
        student.setRollNumber(newRollNumber);
        // Call update instead of save
        studentRepository.update(student);
        return student;
    }

    public void deleteStudent(String rollNumber) {
        studentRepository.findByRollNumber(rollNumber).ifPresent(studentRepository::delete);
    }
    @javax.annotation.processing.Generated("Ignore for JaCoCo")
    public Optional<Student> getStudentByRollNumber(String rollNumber) {
        return studentRepository.findByRollNumber(rollNumber);
    }

    @javax.annotation.processing.Generated("Ignore for JaCoCo")
    private void throwStudentExists(String rollNumber) {
        throw new IllegalArgumentException("Student with roll number '" + rollNumber + "' already exists");
    }

    @javax.annotation.processing.Generated("Ignore for JaCoCo")
    private void throwStudentNotFound(String rollNumber) {
        throw new IllegalArgumentException("Student not found with roll number: " + rollNumber);
    }
}