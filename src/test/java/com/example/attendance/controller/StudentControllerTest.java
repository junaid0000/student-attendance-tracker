package com.example.attendance.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import com.example.attendance.model.Student;
import com.example.attendance.repository.StudentRepository;
import org.junit.Test;

public class StudentControllerTest {
    
    @Test
    public void testAddStudent() {
        // ARRANGE
        StudentRepository mockRepo = mock(StudentRepository.class);
        StudentController controller = new StudentController(mockRepo);
        
        Student testStudent = new Student("Junaid", "7131056");
        when(mockRepo.save(any(Student.class))).thenReturn(testStudent);
        
        // ACT
        Student result = controller.addStudent("Junaid", "7131056");
        
        // ASSERT
        assertNotNull("Should return a student object", result);
        assertEquals("Student name should be Junaid", "Junaid", result.getName());
        assertEquals("Roll number should be 7131056", "7131056", result.getRollNumber());
        verify(mockRepo, times(1)).save(any(Student.class));
    }
}