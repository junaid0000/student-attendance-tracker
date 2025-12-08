package com.example.attendance.controller;

import com.example.attendance.model.AttendanceRecord;
import com.example.attendance.model.Student;
import com.example.attendance.repository.AttendanceRepository;
import com.example.attendance.repository.StudentRepository;
import java.util.Date;

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
}