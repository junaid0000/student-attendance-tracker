	package com.example.attendance.model;
	
	import static org.junit.Assert.*;
	import static org.assertj.core.api.Assertions.assertThat;
	import org.junit.Test;
	import static org.junit.Assert.assertFalse;
	import static org.junit.Assert.assertTrue;
	public class StudentTest {
		
	@Test
	public void testCreateStudentWithAutoId() {
	    Student student = new Student("Junaid", "7131056");
	    assertThat(student.getId()).isEqualTo("STU001");
	}
	@Test
	public void testStudentEquals() {
	    Student student1 = new Student("Junaid", "7131056");
	    Student student2 = new Student("Junaid", "7131056");
	    
	    // Give them same ID for equality test
	    student2.setId(student1.getId());
	    
	    // Multiple assertions in one test as in book Money Example
	    assertThat(student1).isEqualTo(student2);
	    assertThat(student1.equals(null)).isFalse();
	    assertThat(student1.equals("String")).isFalse();
	    assertThat(student1).isEqualTo(student1);
	}
	@Test
	public void testStudentGetId() {
	    Student student = new Student("Junaid", "7131056");
	    String id = student.getId();
	    assertNotNull("Student ID should not be null", id);
	    assertFalse("Student ID should not be empty", id.isEmpty());
	}
	@Test
	public void testStudentGetName() {
	    Student student = new Student("Junaid", "7131056");
	    assertEquals("Student name should match", 
	                "Junaid", student.getName());
	}
	@Test
	public void testStudentGetRollNumber() {
	    Student student = new Student("Junaid", "7131056");
	    assertEquals("Student roll number should match", 
	                "7131056", student.getRollNumber());
	}
	@Test
	public void testStudentHashCode() {
	    Student student1 = new Student("Junaid", "7131056");
	    Student student2 = new Student("Junaid", "7131056");
	    // Give them same ID for hashcode test
	    student2.setId(student1.getId());
	    assertEquals("Equal students should have same hashcode", 
	                student1.hashCode(), student2.hashCode());
	 // ADD THIS LINE AT THE END:
	    
	    // For Jacoco: Test hashCode when id is null (covers if (id == null) branch)
	    Student student3 = new Student("Bob", "R003");
	    student3.setId(null);
	    assertEquals(0, student3.hashCode());
	}
	
	@Test
	public void testStudentSetName() {
	    Student student = new Student("OldName", "7131056");
	    student.setName("NewName");
	    assertEquals("Student name should be updated", 
	                "NewName", student.getName());
	}
	@Test
	public void testStudentSetRollNumber() {
	    Student student = new Student("Junaid", "OldRoll");
	    student.setRollNumber("NewRoll");
	    assertEquals("Student roll number should be updated", 
	                "NewRoll", student.getRollNumber());
	}

}
 
