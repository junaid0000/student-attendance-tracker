package attendance.tracker.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class testStudentSetRollNumber {

	@Test
	public void testStudentSetRollNumber() {
	    Student student = new Student("Junaid", "OldRoll");
	    student.setRollNumber("NewRoll");
	    assertEquals("Student roll number should be updated", 
	                "NewRoll", student.getRollNumber());
	}

}
