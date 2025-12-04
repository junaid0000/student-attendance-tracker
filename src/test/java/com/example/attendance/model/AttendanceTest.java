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
		 // setup
	    Attendance att1 = new Attendance("STU001", new Date(), true);
	    Attendance att2 = new Attendance("STU002", new Date(), false);
	    
	    // this == obj
	    assertEquals(att1, att1);
	    
	    // obj == null
	    assertFalse(att1.equals(null));
	    
	  
	    assertFalse(att1.equals("String"));
	    
	    //  id == null AND return other.id == null (true case)
	    att1.setId(null);
	    att2.setId(null);
	    assertEquals(att1, att2);
	    
	    //  return other.id == null (false case)
	    Attendance att3 = new Attendance("STU003", new Date(), true);
	    assertFalse(att1.equals(att3));
	    
	    // return id.equals(other.id) (true case)
	    Attendance att4 = new Attendance("STU004", new Date(), true);
	    Attendance att5 = new Attendance("STU005", new Date(), false);
	    att4.setId("CUSTOM");
	    att5.setId("CUSTOM");
	    assertEquals(att4, att5);
	
	}
        @Test
    	public void testAttendanceGetDate() {
        	// setup
    	    Date date = new Date();
    	    Attendance attendance = new Attendance("STU001", date, true);
    	    assertEquals("Attendance date should match", 
    	                date, attendance.getDate());
    	}
        @Test
    	public void testAttendanceGetStudentId() {
        	// setup
    	    Attendance attendance = new Attendance("STU001", new Date(), true);
    	    assertEquals("Attendance student ID should match", 
    	                "STU001", attendance.getStudentId());
    	}
        @Test
        public void testAttendanceHashCode() {
        	// setup
            Attendance att1 = new Attendance("STU001", new Date(), true);
            
            
            att1.setId(null);
            assertEquals(0, att1.hashCode());
            
            // setup
            Attendance att2 = new Attendance("STU002", new Date(), false);
            att2.setId("ATT001");
            
            assertEquals("ATT001".hashCode(), att2.hashCode());
        }
        @Test
    	public void testAttendanceIsPresent() {
        	// setup
    	    Attendance attendance = new Attendance("STU001", new Date(), true);
    	    assertTrue("Attendance should be present", attendance.isPresent());
    	}
        @Test
    	public void testAttendanceSetDate() {
        	// setup
    	    Attendance attendance = new Attendance("STU001", new Date(), true);
    	    Date newDate = new Date(System.currentTimeMillis() + 100000);
    	    
    	    // exercise
    	    attendance.setDate(newDate);
    	    
    		// verify
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
