package com.example.attendance.model;

import static org.junit.Assert.*;

import org.junit.Test;

import com.example.attendance.model.Student;

public class testStudentHashCode {

	@Test
	public void testStudentHashCode() {
	    Student student1 = new Student("Junaid", "7131056");
	    Student student2 = new Student("Junaid", "7131056");
	    // Give them same ID for hashcode test
	    student2.setId(student1.getId());
	    assertEquals("Equal students should have same hashcode", 
	                student1.hashCode(), student2.hashCode());
	}

}
