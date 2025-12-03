package com.example.attendance.model;

import static org.junit.Assert.*;

import org.junit.Test;

import com.example.attendance.model.Student;

public class testStudentSetName {

	@Test
	public void testStudentSetName() {
	    Student student = new Student("OldName", "7131056");
	    student.setName("NewName");
	    assertEquals("Student name should be updated", 
	                "NewName", student.getName());
	}

}
