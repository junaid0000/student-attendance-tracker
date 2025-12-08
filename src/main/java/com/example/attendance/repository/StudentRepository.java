package com.example.attendance.repository;

import java.util.Optional;

import com.example.attendance.model.Student;

public interface StudentRepository {
    Student save(Student student);
    Optional<Student> findByRollNumber(String rollNumber);
    void delete(Student student); // for delete
}