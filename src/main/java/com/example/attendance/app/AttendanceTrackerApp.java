package com.example.attendance.app;

import com.example.attendance.controller.AttendanceController;
import com.example.attendance.controller.StudentController;
import com.example.attendance.repository.AttendanceRepository;
import com.example.attendance.repository.mongo.StudentMongoRepository;
import com.example.attendance.view.swing.AttendanceTrackerSwingView;
import com.mongodb.MongoClient;
import javax.swing.SwingUtilities;

public class AttendanceTrackerApp {
    public static void main(String[] args) {
        // Parse arguments before lambda
        final String mongoHost = getArgValue(args, "--mongo-host=", "localhost");
        final int mongoPort = Integer.parseInt(getArgValue(args, "--mongo-port=", "27017"));
        final String databaseName = getArgValue(args, "--db-name=", "attendance_db");
        final String studentCollection = getArgValue(args, "--db-student-collection=", "students");
        final String attendanceCollection = getArgValue(args, "--db-attendance-collection=", "attendance");
        
        java.awt.EventQueue.invokeLater(() -> {
            try {
                System.out.println("Starting Attendance Tracker Application...");
                System.out.println("MongoDB Host: " + mongoHost);
                System.out.println("MongoDB Port: " + mongoPort);
                System.out.println("Database: " + databaseName);
                System.out.println("Student Collection: " + studentCollection);
                
                // Create MongoDB connection
                MongoClient mongoClient = new MongoClient(mongoHost, mongoPort);
                
                // Create repository
                StudentMongoRepository studentRepository = new StudentMongoRepository(
                    mongoClient, databaseName, studentCollection);
                
                // Create controller  
                StudentController studentController = new StudentController(studentRepository);
                AttendanceRepository attendanceRepository = null;
				AttendanceController attendanceController = new AttendanceController(attendanceRepository, studentRepository);
                
                // Create UI and pass controller
                AttendanceTrackerSwingView frame = new AttendanceTrackerSwingView();
                frame.setStudentController(studentController);
                frame.setAttendanceController(attendanceController);
                
                //delay to ensure students are loaded before tests start
                SwingUtilities.invokeLater(() -> {
                    try {
                        // Give UI time to load students from database
                        Thread.sleep(500);  // Half second delay
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    frame.setVisible(true);
                });
                
                // Close connection when window closes
                frame.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.out.println("Closing database connection...");
                        mongoClient.close();
                    }
                });
                
                System.out.println("Application started successfully!");
                
            } catch (Exception e) {
                e.printStackTrace();
                javax.swing.JOptionPane.showMessageDialog(null, 
                    "Failed to connect to database. Please start MongoDB.\n" +
                    "Host: " + mongoHost + " Port: " + mongoPort + "\n" +
                    "Error: " + e.getMessage(),
                    "Database Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                
                // Show UI without database
                System.out.println("Falling back to UI-only mode (no database)");
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