package com.example.attendance.model;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import com.example.attendance.model.Attendance;

public class testAttendanceGetStudentId {

	@Test
	public void testAttendanceGetStudentId() {
	    Attendance attendance = new Attendance("STU001", new Date(), true);
	    assertEquals("Attendance student ID should match", 
	                "STU001", attendance.getStudentId());
	}

}
