package attendance.tracker.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class testStudentGetId {

	@Test
	public void testStudentGetId() {
	    Student student = new Student("Junaid", "7131056");
	    String id = student.getId();
	    assertNotNull("Student ID should not be null", id);
	    assertFalse("Student ID should not be empty", id.isEmpty());
	}

}
