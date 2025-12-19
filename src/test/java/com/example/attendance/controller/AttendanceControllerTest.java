package com.example.attendance.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.attendance.model.AttendanceRecord;
import com.example.attendance.model.Student;
import com.example.attendance.repository.AttendanceRepository;
import com.example.attendance.repository.StudentRepository;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AttendanceControllerTest {

    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private AttendanceController attendanceController;

    private AutoCloseable closeable;

    @Before
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @After
    public void tearDown() throws Exception {
        closeable.close();
    }

    // Test for Practicing Mocking 

    @Test
    public void testMarkAttendancePresent() {
        // Arrange
        Student student = new Student("Junaid", "7131056");
        when(studentRepository.findByRollNumber("7131056")).thenReturn(Optional.of(student));

        AttendanceRecord record = new AttendanceRecord(student.getStudentId(), new Date(), true);
        when(attendanceRepository.save(any(AttendanceRecord.class))).thenReturn(record);

        // Act
        AttendanceRecord result = attendanceController.markAttendance("7131056", new Date(), true);

        // Assert
        assertNotNull(result);
        assertTrue(result.isPresent());
        verify(attendanceRepository).save(any(AttendanceRecord.class));
    }

    @Test
    public void testMarkAttendanceAbsent() {
        // Arrange
        Student student = new Student("Junaid", "7131056");
        when(studentRepository.findByRollNumber("7131056")).thenReturn(Optional.of(student));

        AttendanceRecord record = new AttendanceRecord(student.getStudentId(), new Date(), false);
        when(attendanceRepository.save(any(AttendanceRecord.class))).thenReturn(record);

        // Act
        AttendanceRecord result = attendanceController.markAttendance("7131056", new Date(), false);

        // Assert
        assertNotNull(result);
        assertFalse(result.isPresent());
        verify(attendanceRepository).save(any(AttendanceRecord.class));
    }

    @Test
    public void testGetAttendanceByDate() {
        // ARRANGE
        List<AttendanceRecord> mockRecords = Arrays.asList(
            new AttendanceRecord("STU001", new Date(), true),
            new AttendanceRecord("STU002", new Date(), false)
        );
        when(attendanceRepository.findByDate(any(Date.class))).thenReturn(mockRecords);

        // ACT
        List<AttendanceRecord> result = attendanceController.getAttendanceByDate(new Date());

        // ASSERT
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(attendanceRepository).findByDate(any(Date.class));
    }

    @Test
    public void testGetAttendanceByStudent() {
        // ARRANGE
        Student student = new Student("Junaid", "7131056");
        when(studentRepository.findByRollNumber("7131056")).thenReturn(Optional.of(student));

        List<AttendanceRecord> mockRecords = Arrays.asList(
            new AttendanceRecord(student.getStudentId(), new Date(), true),
            new AttendanceRecord(student.getStudentId(), new Date(), false)
        );
        when(attendanceRepository.findByStudentId(student.getStudentId())).thenReturn(mockRecords);

        // ACT
        List<AttendanceRecord> result = attendanceController.getAttendanceByStudent("7131056");

        // ASSERT
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(studentRepository).findByRollNumber("7131056");
        verify(attendanceRepository).findByStudentId(student.getStudentId());
    }

    @Test
    public void testGetAttendancePercentage() {
        // ARRANGE
        Student student = new Student("Junaid", "7131056");
        when(studentRepository.findByRollNumber("7131056")).thenReturn(Optional.of(student));

        List<AttendanceRecord> mockRecords = Arrays.asList(
            new AttendanceRecord(student.getStudentId(), new Date(), true),  // Present
            new AttendanceRecord(student.getStudentId(), new Date(), true),  // Present
            new AttendanceRecord(student.getStudentId(), new Date(), false)  // Absent
        );
        when(attendanceRepository.findByStudentId(student.getStudentId())).thenReturn(mockRecords);

        // ACT
        double result = attendanceController.getAttendancePercentage("7131056");

        // ASSERT: 2 out of 3 = 66.66%
        assertEquals(66.66, result, 0.01);
        verify(studentRepository).findByRollNumber("7131056");
        verify(attendanceRepository).findByStudentId(student.getStudentId());
    }
}
