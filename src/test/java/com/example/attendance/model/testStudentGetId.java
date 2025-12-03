package com.example.attendance.model;

import static org.junit.Assert.*;

import org.junit.Test;

import com.example.attendance.model.Student;

public class testStudentGetId {

	@Test
	public void testStudentGetId() {
	    Student student = new Student("Junaid", "7131056");
	    String id = student.getId();
	    assertNotNull("Student ID should not be null", id);
	    assertFalse("Student ID should not be empty", id.isEmpty());
	}

}
