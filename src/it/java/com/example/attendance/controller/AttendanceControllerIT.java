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
    private static final String ATTENDANCE_DB = "attendance_db";
    private static final String ATTENDANCE_RECORDS_COLLECTION = "attendance_records";
    private static final String STUDENTS_COLLECTION = "students";
    private static final String STUDENT_JUNAID_NAME = "Junaid";
    private static final String STUDENT_JUNAID_ROLL = "7131056";
    private static final String STUDENT_AHMED_NAME = "Ahmed";
    private static final String STUDENT_AHMED_ROLL = "7131057";

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
            new ServerAddress(mongo.getHost(), mongo.getMappedPort(27017))
        );

        attendanceRepository = new AttendanceMongoRepository(client, ATTENDANCE_DB, ATTENDANCE_RECORDS_COLLECTION);
        studentRepository = new StudentMongoRepository(client, ATTENDANCE_DB, STUDENTS_COLLECTION);

        attendanceController = new AttendanceController(attendanceRepository, studentRepository);
        studentController = new StudentController(studentRepository);

        // Clean database
        client.getDatabase(ATTENDANCE_DB).getCollection(ATTENDANCE_RECORDS_COLLECTION).deleteMany(new org.bson.Document());
        client.getDatabase(ATTENDANCE_DB).getCollection(STUDENTS_COLLECTION).deleteMany(new org.bson.Document());
    }

    @After
    public void tearDown() {
        client.close();
    }

    // Mark Attendance Present
    @Test
    public void testMarkAttendancePresent() {
        // Add student first
        studentController.addStudent(STUDENT_JUNAID_NAME, STUDENT_JUNAID_ROLL);

        // Mark attendance as present
        Date today = new Date();
        AttendanceRecord attendanceRecord = attendanceController.markAttendance(STUDENT_JUNAID_ROLL, today, true);

        assertThat(attendanceRecord).isNotNull();
        assertThat(attendanceRecord.isPresent()).isTrue();
    }

    // Mark Attendance Absent
    @Test
    public void testMarkAttendanceAbsent() {
        // Add student first
        studentController.addStudent(STUDENT_AHMED_NAME, STUDENT_AHMED_ROLL);

        // Mark attendance as absent
        Date today = new Date();
        AttendanceRecord attendanceRecord = attendanceController.markAttendance(STUDENT_AHMED_ROLL, today, false);

        assertThat(attendanceRecord).isNotNull();
        assertThat(attendanceRecord.isPresent()).isFalse();
    }

    // Get Attendance By Date
    @Test
    public void testGetAttendanceByDate() {
        // Add students
        studentController.addStudent(STUDENT_JUNAID_NAME, STUDENT_JUNAID_ROLL);
        studentController.addStudent(STUDENT_AHMED_NAME, STUDENT_AHMED_ROLL);

        // Mark attendance for same date
        Date today = new Date();
        attendanceController.markAttendance(STUDENT_JUNAID_ROLL, today, true);
        attendanceController.markAttendance(STUDENT_AHMED_ROLL, today, false);

        // Get attendance by date
        List<AttendanceRecord> records = attendanceController.getAttendanceByDate(today);

        assertThat(records).hasSize(2);
    }

    // Get Attendance By Student
    @Test
    public void testGetAttendanceByStudent() {
        // Add student
        studentController.addStudent(STUDENT_JUNAID_NAME, STUDENT_JUNAID_ROLL);

        // Mark multiple attendances
        Date today = new Date();
        Date yesterday = new Date(System.currentTimeMillis() - 86400000);

        attendanceController.markAttendance(STUDENT_JUNAID_ROLL, today, true);
        attendanceController.markAttendance(STUDENT_JUNAID_ROLL, yesterday, false);

        // Get attendance by student
        List<AttendanceRecord> records = attendanceController.getAttendanceByStudent(STUDENT_JUNAID_ROLL);

        assertThat(records).hasSize(2);
    }

    // Get Attendance Percentage
    @Test
    public void testGetAttendancePercentage() {
        // Add student
        studentController.addStudent(STUDENT_JUNAID_NAME, STUDENT_JUNAID_ROLL);

        // Mark 3 attendances: 2 present, 1 absent
        Date date1 = new Date();
        Date date2 = new Date(System.currentTimeMillis() - 86400000);
        Date date3 = new Date(System.currentTimeMillis() - 172800000);

        attendanceController.markAttendance(STUDENT_JUNAID_ROLL, date1, true);  // Present
        attendanceController.markAttendance(STUDENT_JUNAID_ROLL, date2, true);  // Present
        attendanceController.markAttendance(STUDENT_JUNAID_ROLL, date3, false); // Absent

        // Get percentage
        double percentage = attendanceController.getAttendancePercentage(STUDENT_JUNAID_ROLL);

        // 2 out of 3 = 66.66%
        assertThat(percentage).isEqualTo(66.66, org.assertj.core.api.Assertions.offset(0.01));
    }
}