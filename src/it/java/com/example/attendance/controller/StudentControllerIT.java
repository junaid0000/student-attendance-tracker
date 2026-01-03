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

    @ClassRule
    public static final MongoDBContainer mongo = new MongoDBContainer("mongo:4.4.3");

    private MongoClient client;
    private StudentController studentController;
    private StudentRepository studentRepository;

    @Before
    public void setup() {
        client = new MongoClient(
            new ServerAddress(mongo.getContainerIpAddress(), mongo.getMappedPort(27017))
        );

        studentRepository = new StudentMongoRepository(client, "attendance_db", "students");
        studentController = new StudentController(studentRepository);

        client.getDatabase("attendance_db").getCollection("students").deleteMany(new org.bson.Document());
    }

    @After
    public void tearDown() {
        client.close();
    }
    // add studeent
    @Test
    public void testAddStudent() {
        Student student = studentController.addStudent("Junaid", "7131056");

        assertThat(student).isNotNull();
        assertThat(student.getName()).isEqualTo("Junaid");
        assertThat(student.getRollNumber()).isEqualTo("7131056");
    }
    // update student
    @Test
    public void testUpdateStudent() {
        studentController.addStudent("Junaid", "7131056");
        Student updated = studentController.updateStudent("7131056", "Junaid Updated", "7131057");

        assertThat(updated.getName()).isEqualTo("Junaid Updated");
        assertThat(updated.getRollNumber()).isEqualTo("7131057");
    }
    // delete student
    @Test
    public void testDeleteStudent() {
        studentController.addStudent("Junaid", "7131056");
        studentController.deleteStudent("7131056");

        boolean exists = studentRepository.findByRollNumber("7131056").isPresent();
        assertThat(exists).isFalse();
    }
}