package attendance.tracker.model;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class testAttendanceHashCode {

	@Test
	public void testAttendanceHashCode() {
	    Attendance att1 = new Attendance("STU001", new Date(), true);
	    Attendance att2 = new Attendance("STU002", new Date(), false);
	    // Give them same ID for hashcode test
	    att2.setId(att1.getId());
	    assertEquals("Equal attendances should have same hashcode", 
	                att1.hashCode(), att2.hashCode());
	}

}
