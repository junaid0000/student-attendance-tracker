// File: AttendanceControllerTest.java
package com.example.attendance.controller;

import org.junit.Test;

import com.example.attendance.model.AttendanceRecord;

import static org.junit.Assert.*;
import java.util.Date;

public class AttendanceControllerTest {
    @Test
    public void testMarkAttendancePresent() {
        AttendanceController controller = new AttendanceController();
        AttendanceRecord result = controller.markAttendance("R001", new Date(), true);
        assertNotNull(result);
    }
}