package com.example.attendance.view;

import java.util.List;

import com.example.attendance.model.AttendanceRecord;

public interface AttendanceView {
    // Attendance operations
    void attendanceMarked(AttendanceRecord record);
    void attendanceUpdated(AttendanceRecord record);
    void showAttendanceByDate(List<AttendanceRecord> records);
    void showAttendanceByStudent(List<AttendanceRecord> records);
    void showAttendancePercentage(double percentage);
    void showAttendanceError(String message);
}