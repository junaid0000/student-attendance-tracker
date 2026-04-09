package com.example.attendance.repository;

import java.util.List;
import java.util.Optional;

import com.example.attendance.model.Student;

public interface StudentRepository {
    Student save(Student student); // add students

    Optional<Student> findByRollNumber(String rollNumber);

    void delete(Student student); // for delete

    List<Student> findAll();

    void update(Student student); // for update
}