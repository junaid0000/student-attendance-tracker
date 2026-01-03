package com.example.attendance.controller;

import java.util.Date;
import java.util.List;

import com.example.attendance.model.AttendanceRecord;
import com.example.attendance.model.Student;
import com.example.attendance.repository.AttendanceRepository;
import com.example.attendance.repository.StudentRepository;


public class AttendanceController {
    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;

    public AttendanceController(AttendanceRepository attendanceRepository,
                               StudentRepository studentRepository) {
        this.attendanceRepository = attendanceRepository;
        this.studentRepository = studentRepository;
    }

    public AttendanceRecord markAttendance(String rollNumber, Date date, boolean present) {
        Student student = studentRepository.findByRollNumber(rollNumber).get();
        AttendanceRecord record = new AttendanceRecord(student.getStudentId(), date, present);
        return attendanceRepository.markAttendance(record);
    }

    // get attendance by date
    public List<AttendanceRecord> getAttendanceByDate(Date date) {
        return attendanceRepository.findByDate(date);
    }


    // get attendance by student
    public List<AttendanceRecord> getAttendanceByStudent(String rollNumber) {
        Student student = studentRepository.findByRollNumber(rollNumber).get();
        return attendanceRepository.findByStudentId(student.getStudentId());
    }

    // Implement Percentage Calculation
    public double getAttendancePercentage(String rollNumber) {
        Student student = studentRepository.findByRollNumber(rollNumber).get();
        List<AttendanceRecord> records = attendanceRepository.findByStudentId(student.getStudentId());

        long presentCount = 0;
        for (AttendanceRecord record : records) {
            if (record.isPresent()) {
                presentCount++;
            }
        }

        return (presentCount * 100.0) / records.size();
    }
}