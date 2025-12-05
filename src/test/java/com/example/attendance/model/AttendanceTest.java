package com.example.attendance.model; 
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Date;


import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

public class AttendanceTest {

		@Test
		public void testAttendanceEquals() {
		    // setup
		    Attendance att1 = new Attendance("STU001", new Date(), true);
		    Attendance att2 = new Attendance("STU002", new Date(), false);
		    Attendance attNullId1 = new Attendance("STU003", new Date(), true);
		    Attendance attNullId2 = new Attendance("STU004", new Date(), false);
		    Attendance attCustomId1 = new Attendance("STU005", new Date(), true);
		    Attendance attCustomId2 = new Attendance("STU006", new Date(), false);
		    
		    attNullId1.setId(null);
		    attNullId2.setId(null);
		    attCustomId1.setId("CUSTOM");
		    attCustomId2.setId("CUSTOM");
		    
		    // Multiple assertions in one test as in book  Money Example.
		    assertThat(att1).isEqualTo(att1);
		    assertThat(att1.equals(null)).isFalse();
		    assertThat(att1.equals("String")).isFalse();
		    assertThat(att1).isEqualTo(att2);
		    assertThat(attNullId1).isEqualTo(attNullId2);
		    assertThat(attNullId1).isNotEqualTo(att1);
		    assertThat(attCustomId1).isEqualTo(attCustomId2);
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
            assertThat(attendance.getId()).isEqualTo("ATT001");
        }

}
