package com.example.attendance.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.example.attendance.model.Student;
import com.example.attendance.repository.StudentRepository;
import org.junit.Test;

public class StudentControllerTest {
    
	// Add student
	@Test
	public void testAddStudent() {
	    StudentRepository studentRepository = mock(StudentRepository.class);
	    StudentController studentController = new StudentController(studentRepository);
	    
	    Student student = new Student("Junaid", "7131056");
	    when(studentRepository.save(any(Student.class))).thenReturn(student);
	    
	    Student result = studentController.addStudent("Junaid", "7131056");
	    
	    assertNotNull(result);
	    verify(studentRepository).save(any(Student.class));
	}
    
    //Edit student
    @Test
    public void testUpdateStudent() {
        StudentRepository studentRepository = mock(StudentRepository.class);
        StudentController studentController = new StudentController(studentRepository);
        
        Student student = new Student("Junaid", "7131056");
        when(studentRepository.findByRollNumber("7131056")).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        
        Student result = studentController.updateStudent("7131056", "Junaid Updated", "7131056");
        
        assertNotNull(result);
        verify(studentRepository).findByRollNumber("7131056");
        verify(studentRepository).save(any(Student.class));
    }
    
    //Delete student
    @Test
    public void testDeleteStudent() {
        StudentRepository studentRepository = mock(StudentRepository.class);
        StudentController studentController = new StudentController(studentRepository);
        
        Student student = new Student("Junaid", "7131056");
        when(studentRepository.findByRollNumber("7131056")).thenReturn(Optional.of(student));
        
        studentController.deleteStudent("7131056");
        
        verify(studentRepository).findByRollNumber("7131056");
        verify(studentRepository).delete(student);
    }
    
}