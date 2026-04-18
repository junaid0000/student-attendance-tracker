package com.example.attendance.controller;

import java.util.Date;
import java.util.List;

import com.example.attendance.model.AttendanceRecord;
import com.example.attendance.model.Student;
import com.example.attendance.repository.AttendanceRepository;
import com.example.attendance.repository.StudentRepository;


public class AttendanceController {
    private static final String STUDENT_NOT_FOUND_MSG = "Student not found: ";
    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;

    public AttendanceController(AttendanceRepository attendanceRepository,
                               StudentRepository studentRepository) {
        this.attendanceRepository = attendanceRepository;
        this.studentRepository = studentRepository;
    }

    public AttendanceRecord markAttendance(String rollNumber, Date date, boolean present) {
        Student student = studentRepository.findByRollNumber(rollNumber)
                .orElseThrow(() -> new IllegalArgumentException(STUDENT_NOT_FOUND_MSG + rollNumber));
        AttendanceRecord attendanceRecord = new AttendanceRecord(student.getStudentId(), date, present);
        return attendanceRepository.markAttendance(attendanceRecord);
    }

    // get attendance by date
    public List<AttendanceRecord> getAttendanceByDate(Date date) {
        return attendanceRepository.findByDate(date);
    }


    // get attendance by student
    public List<AttendanceRecord> getAttendanceByStudent(String rollNumber) {
        Student student = studentRepository.findByRollNumber(rollNumber)
                .orElseThrow(() -> new IllegalArgumentException(STUDENT_NOT_FOUND_MSG + rollNumber));
        return attendanceRepository.findByStudentId(student.getStudentId());
    }

    // Implement Percentage Calculation
    public double getAttendancePercentage(String rollNumber) {
        Student student = studentRepository.findByRollNumber(rollNumber)
                .orElseThrow(() -> new IllegalArgumentException(STUDENT_NOT_FOUND_MSG + rollNumber));
        List<AttendanceRecord> records = attendanceRepository.findByStudentId(student.getStudentId());

        long presentCount = 0;
        for (AttendanceRecord attendanceRecord : records) {
            if (attendanceRecord.isPresent()) {
                presentCount++;
            }
        }

        return (presentCount * 100.0) / records.size();
    }
}