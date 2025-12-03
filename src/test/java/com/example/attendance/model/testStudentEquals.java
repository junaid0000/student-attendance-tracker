package com.example.attendance.model;

import static org.junit.Assert.*;

import org.junit.Test;

import com.example.attendance.model.Student;

public class testStudentEquals {

	@Test
	public void testStudentEquals() {
	    Student student1 = new Student("Junaid", "7131056");
	    Student student2 = new Student("Junaid", "7131056");
	    // Give them same ID for equality test
	    student2.setId(student1.getId());
	    assertEquals("Students with same data should be equal", 
	                student1, student2);
	}

}
