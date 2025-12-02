package attendance.tracker.model;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class testAttendanceEquals {

	@Test
	public void testAttendanceEquals() {
	    Attendance att1 = new Attendance("STU001", new Date(), true);
	    Attendance att2 = new Attendance("STU002", new Date(), false);
	    // Give them same ID for equality test
	    att2.setId(att1.getId());
	    assertEquals("Attendances with same ID should be equal", 
	                att1, att2);
	}

}
