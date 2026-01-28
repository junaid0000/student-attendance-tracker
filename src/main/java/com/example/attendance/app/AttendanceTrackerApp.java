package com.example.attendance.app;

import com.example.attendance.view.swing.AttendanceTrackerSwingView;

public class AttendanceTrackerApp {
    public static void main(String[] args) {
        // Create and show the Swing view
        AttendanceTrackerSwingView frame = new AttendanceTrackerSwingView();
        frame.setVisible(true);
    }
}