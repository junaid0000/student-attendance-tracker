package com.example.attendance.model;

import static org.junit.Assert.*;

import org.junit.Test;

import com.example.attendance.model.Student;

public class testStudentGetName {

	@Test
	public void testStudentGetName() {
	    Student student = new Student("Junaid", "7131056");
	    assertEquals("Student name should match", 
	                "Junaid", student.getName());
	}

}
