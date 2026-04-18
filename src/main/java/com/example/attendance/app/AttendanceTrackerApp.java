package com.example.attendance.app;

import javax.swing.SwingUtilities;

import com.example.attendance.controller.AttendanceController;
import com.example.attendance.controller.StudentController;
import com.example.attendance.repository.AttendanceRepository;
import com.example.attendance.repository.mongo.AttendanceMongoRepository;
import com.example.attendance.repository.mongo.StudentMongoRepository;
import com.example.attendance.view.swing.AttendanceTrackerSwingView;
import com.mongodb.MongoClient;

import java.util.logging.Logger;
import java.util.logging.Level;

public class AttendanceTrackerApp {
    private static final Logger LOGGER = Logger.getLogger(AttendanceTrackerApp.class.getName());

    public static void main(String[] args) {
        // Parse arguments before lambda
        final String mongoHost = getArgValue(args, "--mongo-host=", "localhost");
        final int mongoPort = Integer.parseInt(getArgValue(args, "--mongo-port=", "27017"));
        final String databaseName = getArgValue(args, "--db-name=", "attendance_db");
        final String studentCollection = getArgValue(args, "--db-student-collection=", "students");
        final String attendanceCollection = getArgValue(args, "--db-attendance-collection=", "attendance");

        java.awt.EventQueue.invokeLater(() -> {
            try {
                LOGGER.info("Starting Attendance Tracker Application...");
                LOGGER.info(() -> "MongoDB Host: " + mongoHost);
                LOGGER.info(() -> "MongoDB Port: " + mongoPort);
                LOGGER.info(() -> "Database: " + databaseName);
                LOGGER.info(() -> "Student Collection: " + studentCollection);

                // Create MongoDB connection
                MongoClient mongoClient = new MongoClient(mongoHost, mongoPort);

                // Create repository
                StudentMongoRepository studentRepository = new StudentMongoRepository(mongoClient, databaseName,
                        studentCollection);

                // Create controller
                StudentController studentController = new StudentController(studentRepository);
                AttendanceRepository attendanceRepository = new AttendanceMongoRepository(mongoClient, databaseName,
                        attendanceCollection);
                AttendanceController attendanceController = new AttendanceController(attendanceRepository,
                        studentRepository);

                // Create UI and pass controller
                AttendanceTrackerSwingView frame = new AttendanceTrackerSwingView();
                frame.setStudentController(studentController);
                frame.setAttendanceController(attendanceController);

                // Set windows visible
                SwingUtilities.invokeLater(() -> {
                    frame.setVisible(true);
                });

                // Close connection when window closes
                frame.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        LOGGER.info("Closing database connection...");
                        mongoClient.close();
                    }
                });

                LOGGER.info("Application started successfully!");

            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Exception occurred", e);
                javax.swing.JOptionPane.showMessageDialog(null,
                        "Failed to connect to database. Please start MongoDB.\n" + "Host: " + mongoHost + " Port: "
                                + mongoPort + "\n" + "Error: " + e.getMessage(),
                        "Database Error", javax.swing.JOptionPane.ERROR_MESSAGE);

                // Show UI without database
                LOGGER.info("Falling back to UI-only mode (no database)");
                AttendanceTrackerSwingView frame = new AttendanceTrackerSwingView();
                frame.setVisible(true);
            }
        });
    }

    private static String getArgValue(String[] args, String prefix, String defaultValue) {
        for (String arg : args) {
            if (arg.startsWith(prefix)) {
                return arg.substring(prefix.length());
            }
        }
        return defaultValue;
    }
}