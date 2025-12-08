package com.example.attendance.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import com.example.attendance.model.Student;
import com.example.attendance.repository.StudentRepository;
import org.junit.Test;

public class StudentControllerTest {
    
	// Add student
    @Test
    public void testAddStudent() {
        // Sutup
        StudentRepository mockRepo = mock(StudentRepository.class);
        StudentController controller = new StudentController(mockRepo);
        
        Student testStudent = new Student("Junaid", "7131056");
        when(mockRepo.save(any(Student.class))).thenReturn(testStudent);
        
        // exersice
        Student result = controller.addStudent("Junaid", "7131056");
        
        // verify
        assertNotNull("Should return a student object", result);
        assertEquals("Student name should be Junaid", "Junaid", result.getName());
        assertEquals("Roll number should be 7131056", "7131056", result.getRollNumber());
        verify(mockRepo, times(1)).save(any(Student.class));
    }
    
    //Edit student
    @Test
    public void testUpdateStudent() {
        
        StudentRepository mockRepo = mock(StudentRepository.class);
        StudentController controller = new StudentController(mockRepo);
        controller.updateStudent("7131056", "Junaid Updated", "7131056");
    }
    
}