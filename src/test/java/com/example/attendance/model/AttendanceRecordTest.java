package com.example.attendance.model; 
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.Date;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

public class AttendanceRecordTest {  

	@Test
	public void testAttendanceEquals() {
	    // setup
	    AttendanceRecord att1 = new AttendanceRecord("STU001", new Date(), true);  
	    AttendanceRecord att2 = new AttendanceRecord("STU002", new Date(), false);  
	    AttendanceRecord attNullId1 = new AttendanceRecord("STU003", new Date(), true); 
	    AttendanceRecord attNullId2 = new AttendanceRecord("STU004", new Date(), false); 
	    AttendanceRecord attCustomId1 = new AttendanceRecord("STU005", new Date(), true); 
	    AttendanceRecord attCustomId2 = new AttendanceRecord("STU006", new Date(), false);  
	    
	    // exercise
	    attNullId1.setRecordId(null);  
	    attNullId2.setRecordId(null);  
	    attCustomId1.setRecordId("CUSTOM"); 
	    attCustomId2.setRecordId("CUSTOM");  
	    
	    // verify
	    // Multiple assertions in one test as in book example Money Example.
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
	    AttendanceRecord attendance = new AttendanceRecord("STU001", date, true);  
	    
	    // verify
	    assertEquals("Attendance date should match", 
	                date, attendance.getDate());
	}
    
    @Test
	public void testAttendanceGetStudentId() {
    	// setup
	    AttendanceRecord attendance = new AttendanceRecord("STU001", new Date(), true);  
	    
	    // verify
	    assertEquals("Attendance student ID should match", 
	                "STU001", attendance.getStudentId());
	}
    
    @Test
    public void testAttendanceHashCode() {
        // setup
        AttendanceRecord att1 = new AttendanceRecord("STU001", new Date(), true);
        AttendanceRecord att2 = new AttendanceRecord("STU002", new Date(), false);
        
        // exercise
        att1.setRecordId(null);
        att2.setRecordId("ATT001");
        
        // verify
        assertEquals(0, att1.hashCode());
        assertEquals("ATT001".hashCode(), att2.hashCode());
    }
    
    @Test
	public void testAttendanceIsPresent() {
    	// setup
	    AttendanceRecord attendance = new AttendanceRecord("STU001", new Date(), true);  
	    
	    // verify
	    assertTrue("Attendance should be present", attendance.isPresent());
	}
    
    @Test
	public void testAttendanceSetDate() {
    	// setup
	    AttendanceRecord attendance = new AttendanceRecord("STU001", new Date(), true);  
	    Date newDate = new Date(System.currentTimeMillis() + 100000);
	    
	    // exercise
	    attendance.setDate(newDate);
	    
	    // verify
	    assertEquals("Attendance date should be updated", 
	    		newDate, attendance.getDate());
	}
    
    @Test
	public void testAttendanceSetPresent() {
    	// setup
	    AttendanceRecord attendance = new AttendanceRecord("STU001", new Date(), true);  
	    
	    // exercise
	    attendance.setPresent(false);
	    
	    // verify
	    assertFalse("Attendance should be updated to absent", 
	               attendance.isPresent());
	}
    
    @Test
    public void testCreateAttendanceWithAutoId() {
        // setup
        AttendanceRecord attendance = new AttendanceRecord("STU001", new Date(), true);  
        
        // verify
        assertThat(attendance.getRecordId()).isEqualTo("ATT001");  
    }

}