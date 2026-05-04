package com.example.attendance.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.Test;

public class AttendanceRecordTest {
    private static final String UNUSED_STU001 = "STU001";
    private static final String CUSTOM_ID = "CUSTOM";
    private static final String ATT001 = "ATT001";

    @Test
    public void testAttendanceEquals() {
        // setup
        AttendanceRecord att1 = new AttendanceRecord(UNUSED_STU001, new Date(), true);
        AttendanceRecord att2 = new AttendanceRecord("STU002", new Date(), false);
        AttendanceRecord attNullId1 = new AttendanceRecord("STU003", new Date(), true);
        AttendanceRecord attNullId2 = new AttendanceRecord("STU004", new Date(), false);
        AttendanceRecord attCustomId1 = new AttendanceRecord("STU005", new Date(), true);
        AttendanceRecord attCustomId2 = new AttendanceRecord("STU006", new Date(), false);

        // exercise
        attNullId1.setRecordId(null);
        attNullId2.setRecordId(null);
        attCustomId1.setRecordId(CUSTOM_ID);
        attCustomId2.setRecordId(CUSTOM_ID);

        // verify
        assertThat(att1).isEqualTo(att1).isNotNull().isNotEqualTo("String").isEqualTo(att2);

        assertThat(attNullId1).isEqualTo(attNullId2).isNotEqualTo(att1);
        assertThat(attCustomId1).isEqualTo(attCustomId2);
    }

    @Test
    public void testAttendanceGetDate() {
        // setup
        Date date = new Date();
        AttendanceRecord attendance = new AttendanceRecord(UNUSED_STU001, date, true);

        // verify
        assertThat(attendance.getDate()).as("Attendance date should match").isEqualTo(date);
    }

    @Test
    public void testAttendanceGetStudentId() {
        // setup
        AttendanceRecord attendance = new AttendanceRecord(UNUSED_STU001, new Date(), true);

        // verify
        assertThat(attendance.getStudentId()).as("Attendance student ID should match").isEqualTo(UNUSED_STU001);
    }

    @Test
    public void testAttendanceHashCode() {
        // setup
        AttendanceRecord att1 = new AttendanceRecord(UNUSED_STU001, new Date(), true);
        AttendanceRecord att2 = new AttendanceRecord("STU002", new Date(), false);

        // exercise
        att1.setRecordId(null);
        att2.setRecordId(ATT001);

        // verify
        assertThat(att1.hashCode()).isZero();
        assertThat(att2).hasSameHashCodeAs(ATT001);
    }

    @Test
    public void testAttendanceIsPresent() {
        // setup
        AttendanceRecord attendance = new AttendanceRecord("STU001", new Date(), true);

        // verify
        assertThat(attendance.isPresent()).as("Attendance should be present").isTrue();
    }

    @Test
    public void testAttendanceSetDate() {
        // setup
        AttendanceRecord attendance = new AttendanceRecord("STU001", new Date(), true);
        Date newDate = new Date(System.currentTimeMillis() + 100000);

        // exercise
        attendance.setDate(newDate);

        // verify
        assertThat(attendance.getDate()).as("Attendance date should be updated").isEqualTo(newDate);
    }

    @Test
    public void testAttendanceSetPresent() {
        // setup
        AttendanceRecord attendance = new AttendanceRecord("STU001", new Date(), true);

        // exercise
        attendance.setPresent(false);

        // verify
        assertThat(attendance.isPresent()).as("Attendance should be updated to absent").isFalse();
    }

    @Test
    public void testCreateAttendanceWithAutoId() {
        // setup
        AttendanceRecord attendance = new AttendanceRecord(UNUSED_STU001, new Date(), true);

        // verify
        assertThat(attendance.getRecordId()).isEqualTo(ATT001);
    }
}
