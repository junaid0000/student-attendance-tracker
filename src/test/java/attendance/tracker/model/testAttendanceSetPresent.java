package attendance.tracker.model;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class testAttendanceSetPresent {

	@Test
	public void testAttendanceSetPresent() {
	    Attendance attendance = new Attendance("STU001", new Date(), true);
	    attendance.setPresent(false);
	    assertFalse("Attendance should be updated to absent", 
	               attendance.isPresent());
	}

}
