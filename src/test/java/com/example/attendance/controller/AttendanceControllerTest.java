package com.example.attendance.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.attendance.model.AttendanceRecord;
import com.example.attendance.model.Student;
import com.example.attendance.repository.AttendanceRepository;
import com.example.attendance.repository.StudentRepository;

public class AttendanceControllerTest {
    private static final String UNUSED_ROLL_NUMBER = "7131056";
    private static final String UNUSED_NAME = "Junaid";

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

    @Test
    public void testMarkAttendancePresent() {
        // Arrange
        Student student = new Student(UNUSED_NAME, UNUSED_ROLL_NUMBER);
        when(studentRepository.findByRollNumber(UNUSED_ROLL_NUMBER)).thenReturn(Optional.of(student));

        AttendanceRecord attendanceRecord = new AttendanceRecord(student.getStudentId(), new Date(), true);
        when(attendanceRepository.markAttendance(any(AttendanceRecord.class))).thenReturn(attendanceRecord);

        // Act
        AttendanceRecord result = attendanceController.markAttendance("7131056", new Date(), true);

        // Assert
        assertNotNull(result);
        assertTrue(result.isPresent());
        verify(attendanceRepository).markAttendance(any(AttendanceRecord.class));
    }

    @Test
    public void testMarkAttendanceAbsent() {
        // Arrange
        Student student = new Student(UNUSED_NAME, UNUSED_ROLL_NUMBER);
        when(studentRepository.findByRollNumber(UNUSED_ROLL_NUMBER)).thenReturn(Optional.of(student));

        AttendanceRecord attendanceRecord = new AttendanceRecord(student.getStudentId(), new Date(), false);
        when(attendanceRepository.markAttendance(any(AttendanceRecord.class))).thenReturn(attendanceRecord);

        // Act
        AttendanceRecord result = attendanceController.markAttendance("7131056", new Date(), false);

        // Assert
        assertNotNull(result);
        assertFalse(result.isPresent());
        verify(attendanceRepository).markAttendance(any(AttendanceRecord.class));
    }

    @Test
    public void testGetAttendanceByDate() {
        // ARRANGE
        List<AttendanceRecord> mockRecords = Arrays.asList(new AttendanceRecord("STU001", new Date(), true),
                new AttendanceRecord("STU002", new Date(), false));
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
        Student student = new Student(UNUSED_NAME, UNUSED_ROLL_NUMBER);
        when(studentRepository.findByRollNumber(UNUSED_ROLL_NUMBER)).thenReturn(Optional.of(student));

        List<AttendanceRecord> mockRecords = Arrays.asList(
                new AttendanceRecord(student.getStudentId(), new Date(), true),
                new AttendanceRecord(student.getStudentId(), new Date(), false));
        when(attendanceRepository.findByStudentId(student.getStudentId())).thenReturn(mockRecords);

        // ACT
        List<AttendanceRecord> result = attendanceController.getAttendanceByStudent(UNUSED_ROLL_NUMBER);

        // ASSERT
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(studentRepository).findByRollNumber(UNUSED_ROLL_NUMBER);
        verify(attendanceRepository).findByStudentId(student.getStudentId());
    }

    @Test
    public void testGetAttendancePercentage() {
        // ARRANGE
        Student student = new Student(UNUSED_NAME, UNUSED_ROLL_NUMBER);
        when(studentRepository.findByRollNumber(UNUSED_ROLL_NUMBER)).thenReturn(Optional.of(student));

        List<AttendanceRecord> mockRecords = Arrays.asList(
                new AttendanceRecord(student.getStudentId(), new Date(), true), // Present
                new AttendanceRecord(student.getStudentId(), new Date(), true), // Present
                new AttendanceRecord(student.getStudentId(), new Date(), false) // Absent
        );
        when(attendanceRepository.findByStudentId(student.getStudentId())).thenReturn(mockRecords);

        // ACT
        double result = attendanceController.getAttendancePercentage(UNUSED_ROLL_NUMBER);

        // ASSERT: 2 out of 3 = 66.66%
        assertEquals(66.66, result, 0.01);
        verify(studentRepository).findByRollNumber(UNUSED_ROLL_NUMBER);
        verify(attendanceRepository).findByStudentId(student.getStudentId());
    }
}
