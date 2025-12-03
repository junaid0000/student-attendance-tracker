package com.example.attendance.model;

import static org.junit.Assert.*;

import org.junit.Test;

import com.example.attendance.model.Student;

public class testCreateStudentWithAutoId {
	
	@Test
	public void testCreateStudentWithAutoId() {
	    Student student = new Student("Junaid", "7131056");
	    assertNotNull("Student ID should be automatically assigned",  
	                 student.getId());
	}

}
 
