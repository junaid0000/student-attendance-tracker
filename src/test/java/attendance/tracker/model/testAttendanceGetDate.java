package attendance.tracker.model;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class testAttendanceGetDate {

	@Test
	public void testAttendanceGetDate() {
	    Date date = new Date();
	    Attendance attendance = new Attendance("STU001", date, true);
	    assertEquals("Attendance date should match", 
	                date, attendance.getDate());
	}

}
