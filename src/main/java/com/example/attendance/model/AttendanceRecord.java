package com.example.attendance.model;

import java.util.Date;
import java.util.Objects;

public class AttendanceRecord {
    private String recordId;
    private String studentId;
    private Date date;
    private boolean present;

    // constructor
    public AttendanceRecord(String recordId, Date date, boolean present, String studentId) {
        this.recordId = recordId;
        this.date = date != null ? new Date(date.getTime()) : null;
        this.present = present;
        this.studentId = studentId;
    }

    // constructor
    public AttendanceRecord(String studentId, Date date, boolean present) {
        this.studentId = studentId;
        this.date = date != null ? new Date(date.getTime()) : null;
        this.present = present;
        this.recordId = "ATT001";
    }

    public String getRecordId() {
        return recordId;
    }
    public String getStudentId() {
        return studentId;
    }
    public Date getDate() {
        return date != null ? new Date(date.getTime()) : null;
    }
    public boolean isPresent() {
        return present;
    }
    public void setDate(Date date) {
        this.date = date != null ? new Date(date.getTime()) : null;
    }
    public void setPresent(boolean present) {
        this.present = present;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AttendanceRecord other = (AttendanceRecord) obj;
        return Objects.equals(recordId, other.recordId);
    }
    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }
    @Override
    public int hashCode() {
        return Objects.hashCode(recordId);
    }
}