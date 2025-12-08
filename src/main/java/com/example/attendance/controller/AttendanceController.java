package com.example.attendance.controller;

import com.example.attendance.model.AttendanceRecord;
import com.example.attendance.model.Student;
import com.example.attendance.repository.AttendanceRepository;
import com.example.attendance.repository.StudentRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AttendanceController {
    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    
    public AttendanceController(AttendanceRepository attendanceRepository, 
                               StudentRepository studentRepository) {
        this.attendanceRepository = attendanceRepository;
        this.studentRepository = studentRepository;
    }
    
    public AttendanceRecord markAttendance(String rollNumber, Date date, boolean present) {
        Student student = studentRepository.findByRollNumber(rollNumber)
            .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        
        AttendanceRecord record = new AttendanceRecord(student.getStudentId(), date, present);
        return attendanceRepository.save(record);
    }
    // get attendance by date
    public List<AttendanceRecord> getAttendanceByDate(Date date) {
        return attendanceRepository.findByDate(date);
    }
    // get attendance by student
    public List<AttendanceRecord> getAttendanceByStudent(String rollNumber) {
        Student student = studentRepository.findByRollNumber(rollNumber)
            .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        
        return attendanceRepository.findByStudentId(student.getStudentId());
    }
    public double getAttendancePercentage(String rollNumber) {
        // Will implement later
        return 0.0;
    }
    
}