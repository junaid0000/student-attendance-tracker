package com.example.attendance.view;

import java.util.List;

import com.example.attendance.model.AttendanceRecord;
import com.example.attendance.model.Student;

public interface AttendanceTrackerView {
	
    // Student CRUD operations
    void studentAdded(Student student);
    void studentUpdated(Student student);
    void studentDeleted(Student student);
    void showStudentError(String message, Student student);
    
    // Attendance operations
    void attendanceMarked(AttendanceRecord record);
    void attendanceUpdated(AttendanceRecord record);
    void showAttendanceByDate(List<AttendanceRecord> records);
    void showAttendanceByStudent(List<AttendanceRecord> records);
    void showAttendancePercentage(double percentage);
    void showAttendanceError(String message);
}