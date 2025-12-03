package com.example.attendance.model;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import com.example.attendance.model.Attendance;

public class testAttendanceIsPresent {

	@Test
	public void testAttendanceIsPresent() {
	    Attendance attendance = new Attendance("STU001", new Date(), true);
	    assertTrue("Attendance should be present", attendance.isPresent());
	}

}
