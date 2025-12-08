package com.example.attendance.repository;

import com.example.attendance.model.Student;

public interface StudentRepository {
    Student save(Student student);
}