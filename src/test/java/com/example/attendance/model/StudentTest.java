package com.example.attendance.model;

import static org.junit.Assert.*;

import org.junit.Test;

import com.example.attendance.model.Student;
import static org.junit.Assert.assertFalse;

import static org.junit.Assert.assertTrue;

public class StudentTest {
	
	@Test
	public void testCreateStudentWithAutoId() {
	    Student student = new Student("Junaid", "7131056");
	    assertNotNull("Student ID should be automatically assigned",  
	                 student.getId());
	}
	@Test
	public void testStudentEquals() {
	    Student student1 = new Student("Junaid", "7131056");
	    Student student2 = new Student("Junaid", "7131056");
	    // Give them same ID for equality test
	    student2.setId(student1.getId());
	    assertEquals("Students with same data should be equal", 
	                student1, student2);
	 // For Jacoco: Test equals with null (covers if (obj == null) branch)
	    assertFalse("Student should not equal null", student1.equals(null));
	    
	    // For Jacoco: Test equals with different class (covers if (getClass() != obj.getClass()))
	    assertFalse("Student should not equal String", student1.equals("I'm a String"));
	    
	    // For Jacoco: Test equals with same object (covers if (this == obj))
	    assertTrue("Student should equal itself", student1.equals(student1));
	
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
 
