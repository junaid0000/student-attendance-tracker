package com.example.attendance.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class StudentTest {
    private static final String UNUSED_NAME = "Junaid";
    private static final String UNUSED_ROLL_NUMBER = "7131056";

    @Test
    public void testCreateStudentWithAutoId() {
        // setup
        Student student = new Student(UNUSED_NAME, UNUSED_ROLL_NUMBER);

        // verify
        assertThat(student.getStudentId())
            .as("Student ID should be automatically assigned")
            .isNotNull();
    }

    @Test
    public void testStudentEquals() {
        // setup
        Student student1 = new Student(UNUSED_NAME, UNUSED_ROLL_NUMBER);
        Student student2 = new Student(UNUSED_NAME, UNUSED_ROLL_NUMBER);

        // exercise so here Give them same ID for equality test
        student2.setStudentId(student1.getStudentId());

        // verify
        assertThat(student1)
            .as("Students with same data should be equal")
            .isEqualTo(student2)
            .isNotEqualTo(null)
            .isNotEqualTo("I'm a String")
            .isEqualTo(student1);
    }

    @Test
    public void testStudentGetId() {
        // setup
        Student student = new Student(UNUSED_NAME, UNUSED_ROLL_NUMBER);
        String id = student.getStudentId();

        // verify
        assertThat(id)
            .as("Student ID should not be null")
            .isNotNull()
            .as("Student ID should not be empty")
            .isNotEmpty();
    }

    @Test
    public void testStudentGetName() {
        // setup
        Student student = new Student(UNUSED_NAME, UNUSED_ROLL_NUMBER);

        // verify
        assertThat(student.getName())
            .as("Student name should match")
            .isEqualTo(UNUSED_NAME);
    }

    @Test
    public void testStudentGetRollNumber() {
        // setup
        Student student = new Student(UNUSED_NAME, UNUSED_ROLL_NUMBER);

        // verify
        assertThat(student.getRollNumber())
            .as("Student roll number should match")
            .isEqualTo(UNUSED_ROLL_NUMBER);
    }

    @Test
    public void testStudentHashCode() {
        // setup
        Student student1 = new Student(UNUSED_NAME, UNUSED_ROLL_NUMBER);
        Student student2 = new Student("Other Name", UNUSED_ROLL_NUMBER);

        // verify
        assertThat(student1.hashCode())
            .as("Students with same roll number should have same hashcode")
            .isEqualTo(student2.hashCode());
        
        Student student3 = new Student("Bob", "R003");
        assertThat(student3.hashCode()).isNotEqualTo(student1.hashCode());
    }

    @Test
    public void testStudentSetName() {
        // setup
        Student student = new Student("OldName", "7131056");

        // exercise
        student.setName("NewName");

        // verify
        assertThat(student.getName())
            .as("Student name should be updated")
            .isEqualTo("NewName");
    }

    @Test
    public void testStudentSetRollNumber() {
        // setup
        Student student = new Student("Junaid", "OldRoll");

        // exercise
        student.setRollNumber("NewRoll");

        // verify
        assertThat(student.getRollNumber())
            .as("Student roll number should be updated")
            .isEqualTo("NewRoll");
    }
}