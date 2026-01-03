package com.example.attendance.model;

import java.util.Date;

public class AttendanceRecord {
    private String recordId;
    private String studentId;
    private Date date;
    private boolean present;

    // constructor
    public AttendanceRecord(String recordId, Date date, boolean present, String studentId) {
        this.recordId = recordId;
        this.date = date;
        this.present = present;
        this.studentId = studentId;
    }

    // constructor
    public AttendanceRecord(String studentId, Date date, boolean present) {
        this.studentId = studentId;
        this.date = date;
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
        return date;
    }
    public boolean isPresent() {
        return present;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public void setPresent(boolean present) {
        this.present = present;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }
        AttendanceRecord other = (AttendanceRecord) obj;
        if (recordId == null) {
            return other.recordId == null;
        } else {
            return recordId.equals(other.recordId);
        }
    }
    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }
    @Override
    public int hashCode() {
        if (recordId == null) {
            return 0;
        }
        return recordId.hashCode();
    }
}