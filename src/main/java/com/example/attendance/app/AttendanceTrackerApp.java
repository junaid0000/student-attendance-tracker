package com.example.attendance.app;

import com.example.attendance.view.swing.AttendanceTrackerSwingView;

public class AttendanceTrackerApp {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            AttendanceTrackerSwingView frame = new AttendanceTrackerSwingView();
            frame.setVisible(true);
        });
    }
}