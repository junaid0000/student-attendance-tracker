package com.example.attendance.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.MongoDBContainer;

import com.example.attendance.model.AttendanceRecord;
import com.example.attendance.repository.mongo.AttendanceMongoRepository;
import com.example.attendance.repository.mongo.StudentMongoRepository;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

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
        client = new MongoClient(
            new ServerAddress(mongo.getContainerIpAddress(), mongo.getMappedPort(27017))
        );

        attendanceRepository = new AttendanceMongoRepository(client, "attendance_db", "attendance_records");
        studentRepository = new StudentMongoRepository(client, "attendance_db", "students");

        attendanceController = new AttendanceController(attendanceRepository, studentRepository);
        studentController = new StudentController(studentRepository);

        // Clean database
        client.getDatabase("attendance_db").getCollection("attendance_records").deleteMany(new org.bson.Document());
        client.getDatabase("attendance_db").getCollection("students").deleteMany(new org.bson.Document());
    }

    @After
    public void tearDown() {
        client.close();
    }

    // Mark Attendance Present
    @Test
    public void testMarkAttendancePresent() {
        // Add student first
        studentController.addStudent("Junaid", "7131056");

        // Mark attendance as present
        Date today = new Date();
        AttendanceRecord record = attendanceController.markAttendance("7131056", today, true);

        assertThat(record).isNotNull();
        assertThat(record.isPresent()).isTrue();
    }

    // Mark Attendance Absent
    @Test
    public void testMarkAttendanceAbsent() {
        // Add student first
        studentController.addStudent("Ahmed", "7131057");

        // Mark attendance as absent
        Date today = new Date();
        AttendanceRecord record = attendanceController.markAttendance("7131057", today, false);

        assertThat(record).isNotNull();
        assertThat(record.isPresent()).isFalse();
    }

    // Get Attendance By Date
    @Test
    public void testGetAttendanceByDate() {
        // Add students
        studentController.addStudent("Junaid", "7131056");
        studentController.addStudent("Ahmed", "7131057");

        // Mark attendance for same date
        Date today = new Date();
        attendanceController.markAttendance("7131056", today, true);
        attendanceController.markAttendance("7131057", today, false);

        // Get attendance by date
        List<AttendanceRecord> records = attendanceController.getAttendanceByDate(today);

        assertThat(records).hasSize(2);
    }

    // Get Attendance By Student
    @Test
    public void testGetAttendanceByStudent() {
        // Add student
        studentController.addStudent("Junaid", "7131056");

        // Mark multiple attendances
        Date today = new Date();
        Date yesterday = new Date(System.currentTimeMillis() - 86400000);

        attendanceController.markAttendance("7131056", today, true);
        attendanceController.markAttendance("7131056", yesterday, false);

        // Get attendance by student
        List<AttendanceRecord> records = attendanceController.getAttendanceByStudent("7131056");

        assertThat(records).hasSize(2);
    }

    // Get Attendance Percentage
    @Test
    public void testGetAttendancePercentage() {
        // Add student
        studentController.addStudent("Junaid", "7131056");

        // Mark 3 attendances: 2 present, 1 absent
        Date date1 = new Date();
        Date date2 = new Date(System.currentTimeMillis() - 86400000);
        Date date3 = new Date(System.currentTimeMillis() - 172800000);

        attendanceController.markAttendance("7131056", date1, true);  // Present
        attendanceController.markAttendance("7131056", date2, true);  // Present
        attendanceController.markAttendance("7131056", date3, false); // Absent

        // Get percentage
        double percentage = attendanceController.getAttendancePercentage("7131056");

        // 2 out of 3 = 66.66%
        assertThat(percentage).isEqualTo(66.66, org.assertj.core.api.Assertions.offset(0.01));
    }
}