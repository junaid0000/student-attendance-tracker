package attendance.tracker.model;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class testCreateAttendanceWithAutoId {

	@Test
	public void testCreateAttendanceWithAutoId() {
	    Attendance attendance = new Attendance("STU001", new Date(), true);
	    assertNotNull("Attendance ID should be automatically assigned", 
	                 attendance.getId());
	}

}
