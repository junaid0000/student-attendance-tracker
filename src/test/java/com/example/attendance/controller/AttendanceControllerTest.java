package com.example.attendance.controller;

import org.junit.Test;

import com.example.attendance.model.AttendanceRecord;
import com.example.attendance.model.Student;
import com.example.attendance.repository.AttendanceRepository;
import com.example.attendance.repository.StudentRepository;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class AttendanceControllerTest {
	@Test
	public void testMarkAttendancePresent() {
	    AttendanceRepository attendanceRepository = mock(AttendanceRepository.class);
	    StudentRepository studentRepository = mock(StudentRepository.class);
	    AttendanceController attendanceController = new AttendanceController(attendanceRepository, studentRepository);
	    
	    Student student = new Student("Junaid", "7131056");
	    when(studentRepository.findByRollNumber("7131056")).thenReturn(Optional.of(student));
	    
	    AttendanceRecord record = new AttendanceRecord(student.getStudentId(), new Date(), true);
	    when(attendanceRepository.save(any(AttendanceRecord.class))).thenReturn(record);
	    
	    AttendanceRecord result = attendanceController.markAttendance("7131056", new Date(), true);
	    
	    assertNotNull(result);
	    assertTrue(result.isPresent());
	    verify(attendanceRepository).save(any(AttendanceRecord.class));
	}
	@Test
	public void testMarkAttendanceAbsent() {
	    // Arrange
	    AttendanceRepository attendanceRepository = mock(AttendanceRepository.class);
	    StudentRepository studentRepository = mock(StudentRepository.class);
	    AttendanceController controller = new AttendanceController(attendanceRepository, studentRepository);
	    
	    Student student = new Student("Junaid", "7131056");
	    when(studentRepository.findByRollNumber("7131056")).thenReturn(Optional.of(student));
	    
	    AttendanceRecord fakeRecord = new AttendanceRecord(student.getStudentId(), new Date(), false);
	    when(attendanceRepository.save(any(AttendanceRecord.class))).thenReturn(fakeRecord);
	    
	    // Act
	    AttendanceRecord result = controller.markAttendance("7131056", new Date(), false);
	    
	    // Assert
	    assertNotNull(result);
	    assertFalse(result.isPresent());
	}
	@Test
	public void testGetAttendanceByDate() {
	    // ARRANGE
	    AttendanceRepository attendanceRepository = mock(AttendanceRepository.class);
	    StudentRepository studentRepository = mock(StudentRepository.class);
	    AttendanceController controller = new AttendanceController(attendanceRepository, studentRepository);
	    
	    // Mock data
	    List<AttendanceRecord> mockRecords = Arrays.asList(
	        new AttendanceRecord("STU001", new Date(), true),
	        new AttendanceRecord("STU002", new Date(), false)
	    );
	    when(attendanceRepository.findByDate(any(Date.class))).thenReturn(mockRecords);
	    
	    // ACT
	    List<AttendanceRecord> result = controller.getAttendanceByDate(new Date());
	    
	    // ASSERT
	    assertNotNull(result);
	    assertEquals(2, result.size());
	    verify(attendanceRepository).findByDate(any(Date.class));
	}
}