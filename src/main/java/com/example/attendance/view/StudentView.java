package com.example.attendance.view;

import com.example.attendance.model.Student;


public interface StudentView {
    // Student CRUD operations
    void studentAdded(Student student);
    void studentUpdated(Student student);
    void studentDeleted(Student student);
    void showStudentError(String message, Student student);
}