package com.example.attendance.controller;

import com.example.attendance.model.AttendanceRecord;
import java.util.Date;

public class AttendanceController {
    public AttendanceRecord markAttendance(String rollNumber, Date date, boolean present) {
        String studentId = "STU001";
        return new AttendanceRecord(studentId, date, present);
    }
}