package com.example.attendance.model; 
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Date;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

public class AttendanceTest {

        @Test
        public void testAttendanceEquals() {
            Attendance att1 = new Attendance("STU001", new Date(), true);
            Attendance att2 = new Attendance("STU002", new Date(), false);
            // Give  same ID for equality test
            att2.setId(att1.getId());
            assertEquals("Attendances with same ID should be equal", 
                        att1, att2);
        }
        @Test
    	public void testAttendanceGetDate() {
    	    Date date = new Date();
    	    Attendance attendance = new Attendance("STU001", date, true);
    	    assertEquals("Attendance date should match", 
    	                date, attendance.getDate());
    	}
        @Test
    	public void testAttendanceGetStudentId() {
    	    Attendance attendance = new Attendance("STU001", new Date(), true);
    	    assertEquals("Attendance student ID should match", 
    	                "STU001", attendance.getStudentId());
    	}
        @Test
    	public void testAttendanceHashCode() {
    	    Attendance att1 = new Attendance("STU001", new Date(), true);
    	    Attendance att2 = new Attendance("STU002", new Date(), false);
    	    // Give  same ID for hashcode test
    	    att2.setId(att1.getId());
    	    assertEquals("Equal attendances should have same hashcode", 
    	                att1.hashCode(), att2.hashCode());
    	}
        @Test
    	public void testAttendanceIsPresent() {
    	    Attendance attendance = new Attendance("STU001", new Date(), true);
    	    assertTrue("Attendance should be present", attendance.isPresent());
    	}
        @Test
    	public void testAttendanceSetDate() {
    	    Attendance attendance = new Attendance("STU001", new Date(), true);
    	    Date newDate = new Date(System.currentTimeMillis() + 100000);
    	    attendance.setDate(newDate);
    	    assertEquals("Attendance date should be updated", 
    	                newDate, attendance.getDate());
    	}
        @Test
    	public void testAttendanceSetPresent() {
    	    Attendance attendance = new Attendance("STU001", new Date(), true);
    	    attendance.setPresent(false);
    	    assertFalse("Attendance should be updated to absent", 
    	               attendance.isPresent());
    	}
        @Test
    	public void testCreateAttendanceWithAutoId() {
    	    Attendance attendance = new Attendance("STU001", new Date(), true);
    	    assertNotNull("Attendance ID should be automatically assigned", 
    	                 attendance.getId());
    	}

}
