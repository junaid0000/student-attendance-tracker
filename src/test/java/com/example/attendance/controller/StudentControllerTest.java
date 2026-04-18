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
    private static final String UNUSED_NAME = "Junaid";
    private static final String UNUSED_ROLL_NUMBER = "7131056";

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
        Student student = new Student(UNUSED_NAME, UNUSED_ROLL_NUMBER);
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        Student result = studentController.addStudent(UNUSED_NAME, UNUSED_ROLL_NUMBER);

        assertNotNull(result);
        verify(studentRepository).save(any(Student.class));
    }

    // Edit student
    @Test
    public void testUpdateStudent() {
        Student student = new Student(UNUSED_NAME, UNUSED_ROLL_NUMBER);
        when(studentRepository.findByRollNumber(UNUSED_ROLL_NUMBER)).thenReturn(Optional.of(student));
        Student result = studentController.updateStudent(UNUSED_ROLL_NUMBER, UNUSED_NAME + " Updated", UNUSED_ROLL_NUMBER);

        assertNotNull(result);
        verify(studentRepository).findByRollNumber(UNUSED_ROLL_NUMBER);
        verify(studentRepository).update(any(Student.class));
    }


    // Delete student
    @Test
    public void testDeleteStudent() {
        Student student = new Student(UNUSED_NAME, UNUSED_ROLL_NUMBER);
        when(studentRepository.findByRollNumber(UNUSED_ROLL_NUMBER)).thenReturn(Optional.of(student));

        studentController.deleteStudent(UNUSED_ROLL_NUMBER);

        verify(studentRepository).findByRollNumber(UNUSED_ROLL_NUMBER);
        verify(studentRepository).delete(student);
    }
}