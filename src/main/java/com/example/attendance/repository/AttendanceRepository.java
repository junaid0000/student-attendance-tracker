package com.example.attendance.repository;

import com.example.attendance.model.AttendanceRecord;

public interface AttendanceRepository {
    AttendanceRecord save(AttendanceRecord record);
}