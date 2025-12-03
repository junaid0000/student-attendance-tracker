package com.example.attendance.model;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import com.example.attendance.model.Attendance;

public class testAttendanceSetDate {

	@Test
	public void testAttendanceSetDate() {
	    Attendance attendance = new Attendance("STU001", new Date(), true);
	    Date newDate = new Date(System.currentTimeMillis() + 100000);
	    attendance.setDate(newDate);
	    assertEquals("Attendance date should be updated", 
	                newDate, attendance.getDate());
	}

}
