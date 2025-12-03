package com.example.attendance.model;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import com.example.attendance.model.Attendance;

public class testAttendanceGetDate {

	@Test
	public void testAttendanceGetDate() {
	    Date date = new Date();
	    Attendance attendance = new Attendance("STU001", date, true);
	    assertEquals("Attendance date should match", 
	                date, attendance.getDate());
	}

}
