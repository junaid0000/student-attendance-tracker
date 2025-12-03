package com.example.attendance.model;

import static org.junit.Assert.*;

import org.junit.Test;

import com.example.attendance.model.Student;

public class testStudentGetRollNumber {

	@Test
	public void testStudentGetRollNumber() {
	    Student student = new Student("Junaid", "7131056");
	    assertEquals("Student roll number should match", 
	                "7131056", student.getRollNumber());
	}

}
