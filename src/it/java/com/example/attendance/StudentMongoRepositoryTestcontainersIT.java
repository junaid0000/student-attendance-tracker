package com.example.attendance;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.GenericContainer;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.example.attendance.model.Student;
import com.example.attendance.repository.mongo.StudentMongoRepository;
import static org.assertj.core.api.Assertions.assertThat;

public class StudentMongoRepositoryTestcontainersIT {

    @SuppressWarnings("rawtypes")
    @ClassRule
    public static final GenericContainer mongo = 
        new GenericContainer("mongo:4.4.3")
            .withExposedPorts(27017);
    
    private MongoClient client;
    private StudentMongoRepository studentRepository;
    
    // Connect to MongoDB container
    @Before
    public void setup() {
        client = new MongoClient(
            new ServerAddress(
                mongo.getContainerIpAddress(),
                mongo.getMappedPort(27017)
            )
        );
        studentRepository = new StudentMongoRepository(client, "attendance_db", "students");
     // I am adding  for Clean the database before each test because its added more record in database
        client.getDatabase("attendance_db").getCollection("students").deleteMany(new org.bson.Document());
    }
    
    @After
    public void tearDown() {
        client.close();
    }
    
    // /check that we can connect to the container
    @Test
    public void test() {
        client.getDatabase("test").getName();
    }
    
    private void addTestStudentToDatabase(String studentId, String name, String rollNumber) {
        MongoClient tempClient = new MongoClient(
            new ServerAddress(
                mongo.getContainerIpAddress(),
                mongo.getMappedPort(27017)
            )
        );
        
        tempClient.getDatabase("attendance_db")
            .getCollection("students")
            .insertOne(
                new org.bson.Document()
                    .append("studentId", studentId)
                    .append("name", name)
                    .append("rollNumber", rollNumber)
            );
        
        tempClient.close();
    }
    
    //  Add Student
    @Test
    public void testSave() {
        Student student = new Student("S1", "Junaid", "7131056");
        studentRepository.save(student);
        
        Student saved = studentRepository.findById("S1");
        assertThat(saved.getStudentId()).isEqualTo("S1");
        assertThat(saved.getName()).isEqualTo("Junaid");
        assertThat(saved.getRollNumber()).isEqualTo("7131056");
    }

    //  Edit Student  
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

    //  Delete Student
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