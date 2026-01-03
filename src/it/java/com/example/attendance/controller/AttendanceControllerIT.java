package com.example.attendance.controller;

import com.example.attendance.repository.mongo.AttendanceMongoRepository;
import com.example.attendance.repository.mongo.StudentMongoRepository;
import com.example.attendance.model.AttendanceRecord;
import com.example.attendance.model.Student;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.MongoDBContainer;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AttendanceControllerIT {
    
    @ClassRule
    public static final MongoDBContainer mongo = new MongoDBContainer("mongo:4.4.3");
    
    private MongoClient client;
    private AttendanceController attendanceController;
    private StudentController studentController;
    private AttendanceMongoRepository attendanceRepository;
    private StudentMongoRepository studentRepository;
    
    @Before
    public void setup() {
        // Connection to MongoDB container
        client = new MongoClient(
            new ServerAddress(mongo.getContainerIpAddress(), mongo.getMappedPort(27017))
        );
        
        // Initialize repositories
        attendanceRepository = new AttendanceMongoRepository(client, "attendance_db", "attendance_records");
        studentRepository = new StudentMongoRepository(client, "attendance_db", "students");
        
        // Initialize controllers with real repositories
        attendanceController = new AttendanceController(attendanceRepository, studentRepository);
        studentController = new StudentController(studentRepository);
        
        // will Clean database before evry test
        client.getDatabase("attendance_db").getCollection("attendance_records").deleteMany(new org.bson.Document());
        client.getDatabase("attendance_db").getCollection("students").deleteMany(new org.bson.Document());
    }
    
    @After
    public void tearDown() {
        client.close();
    }
    
    // Connectivity test
    @Test
    public void testConnectivity() {
        client.getDatabase("test").getName();
    }
    
    // Mark attendance for a student
    @Test
    public void testMarkAttendance() {
        // First, create a student
        Student student = new Student("S1", "John Doe", "7131056");
        studentRepository.save(student);
        
        // Mark attendance
        Date today = new Date();
        AttendanceRecord result = attendanceController.markAttendance("7131056", today, true);
        
        // Verify
        assertThat(result).isNotNull();
        assertThat(result.isPresent()).isTrue();
        assertThat(result.getStudentId()).isEqualTo(student.getStudentId());
        
        // Check if saved in database
        List<AttendanceRecord> records = attendanceRepository.findByDate(today);
        assertThat(records).hasSize(1);
    }
    
    // Get attendance by date (TDD)
    @Test
    public void testGetAttendanceByDate() {
        // Create student
        Student student = new Student("S1", "John Doe", "7131056");
        studentRepository.save(student);
        
        // Mark attendance for today
        Date today = new Date();
        attendanceController.markAttendance("7131056", today, true);
        
        // Mark another attendance for today
        Student student2 = new Student("S2", "Jane Smith", "7131057");
        studentRepository.save(student2);
        attendanceController.markAttendance("7131057", today, false);
        
        // Get attendance by date
        List<AttendanceRecord> result = attendanceController.getAttendanceByDate(today);
        
        // Verify
        assertThat(result).hasSize(2);
    }
}