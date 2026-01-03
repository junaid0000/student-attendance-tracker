package com.example.attendance.controller;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.attendance.model.Student;
import com.example.attendance.repository.StudentRepository;

public class StudentControllerTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentController studentController;

    private AutoCloseable closeable;

    @Before
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @After
    public void tearDown() throws Exception {
        closeable.close();
    }

    // Add student
    @Test
    public void testAddStudent() {
        Student student = new Student("Junaid", "7131056");
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        Student result = studentController.addStudent("Junaid", "7131056");

        assertNotNull(result);
        verify(studentRepository).save(any(Student.class));
    }

    // Edit student
    @Test
    public void testUpdateStudent() {
        Student student = new Student("Junaid", "7131056");
        when(studentRepository.findByRollNumber("7131056")).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        Student result = studentController.updateStudent("7131056", "Junaid Updated", "7131056");

        assertNotNull(result);
        verify(studentRepository).findByRollNumber("7131056");
        verify(studentRepository).save(any(Student.class));
    }

    // Delete student
    @Test
    public void testDeleteStudent() {
        Student student = new Student("Junaid", "7131056");
        when(studentRepository.findByRollNumber("7131056")).thenReturn(Optional.of(student));

        studentController.deleteStudent("7131056");

        verify(studentRepository).findByRollNumber("7131056");
        verify(studentRepository).delete(student);
    }
}
