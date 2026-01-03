package com.example.attendance.repository;

import java.util.Date;
import java.util.List;

import com.example.attendance.model.AttendanceRecord;

public interface AttendanceRepository {
    AttendanceRecord markAttendance(AttendanceRecord record);
    List<AttendanceRecord> findByDate(Date date);
    List<AttendanceRecord> findByStudentId(String studentId);
}