package com.example.attendance.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.MongoDBContainer;

import com.example.attendance.model.Student;
import com.example.attendance.repository.StudentRepository;
import com.example.attendance.repository.mongo.StudentMongoRepository;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class StudentControllerIT {
    private static final String ATTENDANCE_DB = "attendance_db";
    private static final String STUDENTS_COLLECTION = "students";
    private static final String STUDENT_JUNAID_NAME = "Junaid";
    private static final String STUDENT_JUNAID_ROLL = "7131056";
    private static final String STUDENT_JUNAID_UPDATED_NAME = "Junaid Updated";
    private static final String STUDENT_7131057_ROLL = "7131057";

    @ClassRule
    public static final MongoDBContainer mongo = new MongoDBContainer("mongo:4.4.3");

    private MongoClient client;
    private StudentController studentController;
    private StudentRepository studentRepository;

    @Before
    public void setup() {
        client = new MongoClient(
            new ServerAddress(mongo.getHost(), mongo.getMappedPort(27017))
        );

        studentRepository = new StudentMongoRepository(client, ATTENDANCE_DB, STUDENTS_COLLECTION);
        studentController = new StudentController(studentRepository);

        client.getDatabase(ATTENDANCE_DB).getCollection(STUDENTS_COLLECTION).deleteMany(new org.bson.Document());
    }

    @After
    public void tearDown() {
        client.close();
    }
    // add studeent
    @Test
    public void testAddStudent() {
        Student student = studentController.addStudent(STUDENT_JUNAID_NAME, STUDENT_JUNAID_ROLL);

        assertThat(student).isNotNull();
        assertThat(student.getName()).isEqualTo(STUDENT_JUNAID_NAME);
        assertThat(student.getRollNumber()).isEqualTo(STUDENT_JUNAID_ROLL);
    }
    // update student
    @Test
    public void testUpdateStudent() {
        studentController.addStudent(STUDENT_JUNAID_NAME, STUDENT_JUNAID_ROLL);
        Student updated = studentController.updateStudent(STUDENT_JUNAID_ROLL, STUDENT_JUNAID_UPDATED_NAME, STUDENT_7131057_ROLL);

        assertThat(updated.getName()).isEqualTo(STUDENT_JUNAID_UPDATED_NAME);
        assertThat(updated.getRollNumber()).isEqualTo(STUDENT_7131057_ROLL);
    }
    // delete student
    @Test
    public void testDeleteStudent() {
        studentController.addStudent(STUDENT_JUNAID_NAME, STUDENT_JUNAID_ROLL);
        studentController.deleteStudent(STUDENT_JUNAID_ROLL);

        boolean exists = studentRepository.findByRollNumber(STUDENT_JUNAID_ROLL).isPresent();
        assertThat(exists).isFalse();
    }
}