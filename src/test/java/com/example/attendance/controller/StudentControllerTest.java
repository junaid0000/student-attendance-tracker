// File: StudentControllerTest.java
package com.example.attendance.controller;

import org.junit.Test;

import com.example.attendance.model.Student;

import static org.junit.Assert.*;

public class StudentControllerTest {
    @Test
    public void testAddStudent() {
        // This will fail
        StudentController controller = new StudentController();
        Student result = controller.addStudent("John", "R001");
        assertNotNull(result);
    }
}