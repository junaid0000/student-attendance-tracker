package com.example.attendance.repository.mongo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.MongoDBContainer;

import com.example.attendance.model.Student;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class StudentMongoRepositoryTestcontainersIT {
    private static final String ATTENDANCE_DB = "attendance_db";
    private static final String STUDENTS_COLLECTION = "students";
    private static final String JUNAID = "Junaid";
    private static final String ROLL_NUMBER_JUNAID = "7131056";

    @ClassRule
    public static final MongoDBContainer mongo = new MongoDBContainer("mongo:4.4.3");

    private MongoClient client;
    private StudentMongoRepository studentRepository;

    // Connect to MongoDB container
    @Before
    public void setup() {
        client = new MongoClient(new ServerAddress(mongo.getHost(), mongo.getMappedPort(27017)));
        studentRepository = new StudentMongoRepository(client, ATTENDANCE_DB, STUDENTS_COLLECTION);
        // I am adding thisline for Clean the database before each test because its
        // added more record in database
        client.getDatabase(ATTENDANCE_DB).getCollection(STUDENTS_COLLECTION).deleteMany(new org.bson.Document());
    }

    @After
    public void tearDown() {
        client.close();
    }

    // check that we can connect to the container like check test Connectivity
    @Test
    public void testConnectivity() {
        assertThat(client.getDatabase("test").getName()).isEqualTo("test");
    }

    private void addTestStudentToDatabase(String studentId, String name, String rollNumber) {
        MongoClient tempClient = new MongoClient(
                new ServerAddress(mongo.getHost(), mongo.getMappedPort(27017)));

        tempClient.getDatabase(ATTENDANCE_DB).getCollection(STUDENTS_COLLECTION).insertOne(new org.bson.Document()
                .append("studentId", studentId).append("name", name).append("rollNumber", rollNumber));

        tempClient.close();
    }

    // Add Student
    @Test
    public void testSave() {
        Student student = new Student("S1", JUNAID, ROLL_NUMBER_JUNAID);
        studentRepository.save(student);

        Student saved = studentRepository.findById("S1");
        assertThat(saved.getStudentId()).isEqualTo("S1");
        assertThat(saved.getName()).isEqualTo(JUNAID);
        assertThat(saved.getRollNumber()).isEqualTo(ROLL_NUMBER_JUNAID);
    }

    // Edit Student
    @Test
    public void testUpdate() {
        // First add a student
        addTestStudentToDatabase("S2", "Ahmed", "2023002");

        // Edit the student
        Student updatedStudent = new Student("S2", "Ahmed Khan", "2023002");
        studentRepository.update(updatedStudent);

        // Check if updated
        Student found = studentRepository.findById("S2");
        assertThat(found.getName()).isEqualTo("Ahmed Khan");
    }

    // Delete Student
    @Test
    public void testDelete() {
        // First add a student
        addTestStudentToDatabase("S3", "Awais", "2023003");

        // Delete the student
        studentRepository.delete("S3");

        // Should not find deleted student
        Student found = studentRepository.findById("S3");
        assertThat(found).isNull();
    }
}