package com.example.attendance.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StudentTest {

	@Test
	public void testCreateStudentWithAutoId() {
	    // setup
	    Student student = new Student("Junaid", "7131056");

	    // verify
	    assertThat(student.getStudentId())
	        .as("Student ID should be automatically assigned")
	        .isNotNull();
	}

	@Test
	public void testStudentEquals() {
	    // setup
	    Student student1 = new Student("Junaid", "7131056");
	    Student student2 = new Student("Junaid", "7131056");

	    // exercise - Give them same ID for equality test
	    student2.setStudentId(student1.getStudentId());

	    // verify
	    assertEquals("Students with same data should be equal",
	                student1, student2);
	    assertFalse("Student should not equal null", student1.equals(null));
	    assertFalse("Student should not equal String", student1.equals("I'm a String"));
	    assertTrue("Student should equal itself", student1.equals(student1));
	}

	@Test
	public void testStudentGetId() {
	    // setup
	    Student student = new Student("Junaid", "7131056");
	    String id = student.getStudentId();

	    // verify
	    assertNotNull("Student ID should not be null", id);
	    assertFalse("Student ID should not be empty", id.isEmpty());
	}

	@Test
	public void testStudentGetName() {
	    // setup
	    Student student = new Student("Junaid", "7131056");

	    // verify
	    assertEquals("Student name should match",
	                "Junaid", student.getName());
	}

	@Test
	public void testStudentGetRollNumber() {
	    // setup
	    Student student = new Student("Junaid", "7131056");

	    // verify
	    assertEquals("Student roll number should match",
	                "7131056", student.getRollNumber());
	}

	@Test
	public void testStudentHashCode() {
	    // setup
	    Student student1 = new Student("Junaid", "7131056");
	    Student student2 = new Student("Junaid", "7131056");

	    // exercise
	    student2.setStudentId(student1.getStudentId());

	    // verify
	    assertEquals("Equal students should have same hashcode",
	    student1.hashCode(), student2.hashCode());
	    Student student3 = new Student("Bob", "R003");
	    student3.setStudentId(null);
	    assertEquals(0, student3.hashCode());
	}

	@Test
	public void testStudentSetName() {
	    // setup
	    Student student = new Student("OldName", "7131056");

	    // exercise
	    student.setName("NewName");

	    // verify
	    assertEquals("Student name should be updated",
	                "NewName", student.getName());
	}

	@Test
	public void testStudentSetRollNumber() {
	    // setup
	    Student student = new Student("Junaid", "OldRoll");

	    // exercise
	    student.setRollNumber("NewRoll");

	    // verify
	    assertEquals("Student roll number should be updated",
	                "NewRoll", student.getRollNumber());
	}

}