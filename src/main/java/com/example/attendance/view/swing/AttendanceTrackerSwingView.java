package com.example.attendance.view.swing;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.SwingUtilities;
import javax.swing.BoxLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.attendance.model.Student;
import com.example.attendance.controller.StudentController;
import com.example.attendance.controller.AttendanceController;
import com.example.attendance.model.AttendanceRecord;
import com.example.attendance.view.AttendanceTrackerView;

public class AttendanceTrackerSwingView extends JFrame implements AttendanceTrackerView {

    private static final long serialVersionUID = 1L;
    private JTabbedPane tabbedPane;
    
    // Students Tab Components
    private JTextField textFieldname;
    private JTextField textFieldrollno;
    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JList liststudent;
    private JLabel lblerror;
    
    // Attendance Tab Components
    private JTextField dateFieldattendance;
    private JButton btnmarkAttendance;
    private JButton btnviewByDate;
    private JButton viewByStudentButton;
    private JButton btngetSummary;
    private JList attendanceList;
    private JTextArea attendanceRecordsArea;
    private JLabel attendanceErrorLabel;
    private JLabel summaryLabel;
    private StudentController studentController;
    private AttendanceController attendanceController;
    
    // Attendance panel components
    private JPanel studentsAttendancePanel;
    private Map<String, ButtonGroup> studentAttendanceGroups;
    private List<Student> currentStudentsList;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AttendanceTrackerSwingView frame = new AttendanceTrackerSwingView();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public AttendanceTrackerSwingView() {
        setTitle("Student Attendance Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        
        // Create the tabbed pane
        tabbedPane = new JTabbedPane();
        
        // Create Students Tab
        JPanel studentsPanel = createStudentsPanel();
        tabbedPane.addTab("Students", studentsPanel);
        
        // Create Attendance Tab
        JPanel attendancePanel = createAttendancePanel();
        tabbedPane.addTab("Attendance", attendancePanel);
        
        // Add title at top
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Student Attendance Tracker");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        titlePanel.add(titleLabel);
        
        // Add everything to frame
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(titlePanel, BorderLayout.NORTH);
        getContentPane().add(tabbedPane, BorderLayout.CENTER);
        
        // Initialize collections
        studentAttendanceGroups = new HashMap<>();
        currentStudentsList = new ArrayList<>();
    }
    
    private JPanel createStudentsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        
        // Title for Students tab
        JTextArea txtStudentsTab = new JTextArea();
        txtStudentsTab.setFont(new Font("Monospaced", Font.BOLD, 13));
        txtStudentsTab.setText("============== STUDENTS TAB ==============");
        txtStudentsTab.setBounds(190, 11, 400, 20);
        txtStudentsTab.setEditable(false);
        txtStudentsTab.setOpaque(false);
        panel.add(txtStudentsTab);
        
        // Name label and field
        JLabel lblname = new JLabel("Name:");
        lblname.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblname.setHorizontalAlignment(SwingConstants.RIGHT);
        lblname.setBounds(20, 51, 60, 20);
        panel.add(lblname);
        
        textFieldname = new JTextField();
        textFieldname.setName("studentnameTextBox");
        textFieldname.setBounds(90, 50, 200, 20);
        panel.add(textFieldname);
        textFieldname.setColumns(10);
        // ADD KEY LISTENER
        textFieldname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                updateAddButtonState();
            }
        });
        
        // Roll No label and field
        JLabel lblrollno = new JLabel("Roll No:");
        lblrollno.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblrollno.setHorizontalAlignment(SwingConstants.RIGHT);
        lblrollno.setBounds(20, 80, 60, 20);
        panel.add(lblrollno);
        
        textFieldrollno = new JTextField();
        textFieldrollno.setName("rollnumberTxtBox");
        textFieldrollno.setBounds(90, 80, 200, 20);
        panel.add(textFieldrollno);
        textFieldrollno.setColumns(10);
        // ADD KEY LISTENER 
        textFieldrollno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                updateAddButtonState();
            }
        });
        
        // Buttons
        btnAdd = new JButton("Add");
        btnAdd.setName("addButton"); // Add component name for testing
        btnAdd.setBounds(20, 120, 120, 25);
        btnAdd.setEnabled(false); //btn disable
        panel.add(btnAdd);
        
        // Adding LISTENER 
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addStudentAction();
            }
        });
        
        btnUpdate = new JButton("Update");
        btnUpdate.setName("updateButton"); // Add component name for testing
        btnUpdate.setBounds(150, 120, 120, 25);
        panel.add(btnUpdate);
        
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateSelectedStudent();
            }
        });
        
        btnDelete = new JButton("Delete");
        btnDelete.setName("deleteButton"); // Add component name for testing
        btnDelete.setBounds(280, 120, 120, 25);
        panel.add(btnDelete);
        
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteSelectedStudent();
            }
        });
        
        // Student List label
        JTextArea txtrStudentList = new JTextArea();
        txtrStudentList.setFont(new Font("Monospaced", Font.BOLD, 13));
        txtrStudentList.setText("Student List:");
        txtrStudentList.setBounds(20, 160, 150, 20);
        txtrStudentList.setEditable(false);
        txtrStudentList.setOpaque(false);
        panel.add(txtrStudentList);
        
        // Student List with scroll
        JScrollPane studentScrollPane = new JScrollPane();
        studentScrollPane.setBounds(20, 190, 350, 200);
        panel.add(studentScrollPane);
        
        liststudent = new JList();
        liststudent.setName("studentlist");
        studentScrollPane.setViewportView(liststudent);
        
        // Error label
        lblerror = new JLabel("Error: No errors");
        lblerror.setName("errorLabel"); // Add component name for testing
        lblerror.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblerror.setHorizontalAlignment(SwingConstants.LEFT);
        lblerror.setBounds(20, 400, 350, 20);
        panel.add(lblerror);
        
        return panel;
    }
    
    private void updateAddButtonState() {
        boolean nameNotEmpty = !textFieldname.getText().trim().isEmpty();
        boolean rollNoNotEmpty = !textFieldrollno.getText().trim().isEmpty();
        btnAdd.setEnabled(nameNotEmpty && rollNoNotEmpty);
    }
    
    private JPanel createAttendancePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        
        // Title for Attendance tab
        JTextArea txtAttendanceTab = new JTextArea();
        txtAttendanceTab.setText("============== ATTENDANCE TAB ==============");
        txtAttendanceTab.setFont(new Font("Monospaced", Font.BOLD, 13));
        txtAttendanceTab.setBounds(190, 11, 400, 20);
        txtAttendanceTab.setEditable(false);
        txtAttendanceTab.setOpaque(false);
        panel.add(txtAttendanceTab);
        
        // Date section
        JLabel dateLabel = new JLabel("Date:");
        dateLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        dateLabel.setBounds(20, 50, 50, 20);
        panel.add(dateLabel);
        
        dateFieldattendance = new JTextField();
        dateFieldattendance.setName("dateTextField"); // Add component name for testing
        dateFieldattendance.setText("dd/mm/yyyy");
        dateFieldattendance.setBounds(70, 50, 120, 20);
        panel.add(dateFieldattendance);
        
        // Students attendance area title
        JLabel studentsLabel = new JLabel("Students for Attendance:");
        studentsLabel.setFont(new Font("Tahoma", Font.BOLD, 10));
        studentsLabel.setBounds(20, 90, 180, 20);
        panel.add(studentsLabel);
        
        // Create a panel to hold student attendance controls - DYNAMIC CONTENT
        studentsAttendancePanel = new JPanel();
        studentsAttendancePanel.setLayout(new BoxLayout(studentsAttendancePanel, BoxLayout.Y_AXIS));
        
        JScrollPane attendanceScrollPane = new JScrollPane(studentsAttendancePanel);
        attendanceScrollPane.setBounds(20, 120, 350, 150);
        attendanceScrollPane.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel.add(attendanceScrollPane);
        
        // Mark Attendance button
        btnmarkAttendance = new JButton("Mark Attendance");
        btnmarkAttendance.setName("markAttendanceButton"); // Add component name for testing
        btnmarkAttendance.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                markAttendanceAction();
            }
        });
        btnmarkAttendance.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnmarkAttendance.setBounds(20, 290, 150, 30);
        panel.add(btnmarkAttendance);
        
        // View Attendance section
        JLabel viewLabel = new JLabel("View Attendance:");
        viewLabel.setFont(new Font("Tahoma", Font.BOLD, 10));
        viewLabel.setBounds(20, 340, 120, 20);
        panel.add(viewLabel);
        
        JRadioButton byDateRadio = new JRadioButton("By Date");
        byDateRadio.setSelected(true);
        byDateRadio.setBounds(140, 340, 80, 20);
        panel.add(byDateRadio);
        
        JRadioButton byStudentRadio = new JRadioButton("By Student");
        byStudentRadio.setBounds(220, 340, 100, 20);
        panel.add(byStudentRadio);
        
        // Group the view radio buttons
        ButtonGroup viewGroup = new ButtonGroup();
        viewGroup.add(byDateRadio);
        viewGroup.add(byStudentRadio);
        
        // View Attendance button
        btnviewByDate = new JButton("View Attendance");
        btnviewByDate.setName("viewByDateButton"); // Add compnent name for testing
        btnviewByDate.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnviewByDate.setBounds(20, 370, 150, 30);
        panel.add(btnviewByDate);
        
        // Get Summary button
        btngetSummary = new JButton("Get Summary");
        btngetSummary.setName("getSummaryButton"); // Add component name for testing
        btngetSummary.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btngetSummary.setBounds(180, 370, 150, 30);
        panel.add(btngetSummary);
        
        // Attendance records display
        JLabel recordsLabel = new JLabel("Attendance Records:");
        recordsLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        recordsLabel.setBounds(20, 411, 150, 20);
        panel.add(recordsLabel);
        
        JScrollPane recordsScrollPane = new JScrollPane();
        recordsScrollPane.setBounds(200, 411, 450, 80);
        panel.add(recordsScrollPane);
        
        attendanceRecordsArea = new JTextArea();
        attendanceRecordsArea.setName("attendanceRecordsArea"); // Add compnent name for testing
        recordsScrollPane.setViewportView(attendanceRecordsArea);
        attendanceRecordsArea.setText("No attendance records yet.");
        attendanceRecordsArea.setEditable(false);
        
        // Summary label
        summaryLabel = new JLabel("Attendance: 0%");
        summaryLabel.setName("summaryLabel"); // Add compnent name for testing
        summaryLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        summaryLabel.setBounds(20, 540, 200, 30);
        panel.add(summaryLabel);
        
        // Error label
        attendanceErrorLabel = new JLabel("");
        attendanceErrorLabel.setName("attendanceErrorLabel"); // Add compnent name for testing
        attendanceErrorLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
        attendanceErrorLabel.setForeground(java.awt.Color.RED);
        attendanceErrorLabel.setBounds(20, 570, 450, 20);
        panel.add(attendanceErrorLabel);
        
        return panel;
    }
    
    // NEW METHOD: Refresh attendance panel with students from database
    private void refreshAttendancePanelStudents() {
        if (studentController == null) return;
        
        studentsAttendancePanel.removeAll();
        studentAttendanceGroups.clear();
        currentStudentsList.clear();
        
        try {
            List<Student> students = studentController.getAllStudents();
            currentStudentsList = students;
            
            for (Student student : students) {
                JPanel studentPanel = new JPanel();
                studentPanel.setLayout(null);
                studentPanel.setPreferredSize(new java.awt.Dimension(330, 30));
                
                JLabel studentLabel = new JLabel(student.getName() + " (" + student.getRollNumber() + ")");
                studentLabel.setBounds(10, 5, 150, 20);
                studentPanel.add(studentLabel);
                
                JRadioButton presentRadio = new JRadioButton("Present");
                presentRadio.setBounds(170, 5, 70, 20);
                studentPanel.add(presentRadio);
                
                JRadioButton absentRadio = new JRadioButton("Absent");
                absentRadio.setBounds(250, 5, 70, 20);
                studentPanel.add(absentRadio);
                
                // Group radio buttons for this student
                ButtonGroup group = new ButtonGroup();
                group.add(presentRadio);
                group.add(absentRadio);
                presentRadio.setSelected(true); // Default to Present
                
                studentAttendanceGroups.put(student.getRollNumber(), group);
                studentsAttendancePanel.add(studentPanel);
            }
            
            studentsAttendancePanel.revalidate();
            studentsAttendancePanel.repaint();
            
        } catch (Exception e) {
            attendanceErrorLabel.setText("Error loading students: " + e.getMessage());
        }
    }
    
    // Mark attendance action
    private void markAttendanceAction() {
        if (attendanceController == null) {
            attendanceErrorLabel.setText("Attendance controller not set");
            return;
        }
        
        // Use current date
        java.util.Date date = new java.util.Date();
        
        boolean anyMarked = false;
        int markedCount = 0;
        
        // Mark attendance for each student
        for (Student student : currentStudentsList) {
            try {
                // Mark all as present for simplicity
                boolean isPresent = true;
                AttendanceRecord record = attendanceController.markAttendance(
                    student.getRollNumber(), date, isPresent);
                markedCount++;
                anyMarked = true;
            } catch (Exception e) {
                attendanceErrorLabel.setText("Error: " + e.getMessage());
                return;
            }
        }
        
        if (anyMarked) {
            attendanceErrorLabel.setText("Attendance marked for " + markedCount + " student(s)");
            // Update UI
            AttendanceRecord dummyRecord = new AttendanceRecord("temp", date, true, "temp");
            attendanceMarked(dummyRecord);
        } else {
            attendanceErrorLabel.setText("No students to mark attendance");
        }
    }
    
    // Interface Implementation
 
    @Override
    public void studentAdded(Student student) {
        SwingUtilities.invokeLater(() -> {
            // Update error label
            lblerror.setText("Student added: " + student.getName());
            // Refresh the student list from database
            loadStudentsFromDatabase();
            // Refresh attendance panel with new student
            refreshAttendancePanelStudents();
        });
    }
    
    @Override
    public void studentUpdated(Student student) {
        SwingUtilities.invokeLater(() -> {
            lblerror.setText("Student updated: " + student.getName());
            loadStudentsFromDatabase();
            refreshAttendancePanelStudents();
        });
    }
    
    @Override
    public void studentDeleted(Student student) {
        SwingUtilities.invokeLater(() -> {
            String message = (student != null) ? 
                "Student deleted: " + student.getName() : 
                "Student deleted successfully";
            lblerror.setText(message);
            loadStudentsFromDatabase();
            refreshAttendancePanelStudents();
        });
    }
    
    @Override
    public void showStudentError(String message, Student student) {
        SwingUtilities.invokeLater(() -> {
            lblerror.setText("Error: " + message);
        });
    }
    
    @Override
    public void attendanceMarked(AttendanceRecord record) {
        SwingUtilities.invokeLater(() -> {
            attendanceErrorLabel.setText("Attendance marked for student ID: " + record.getStudentId());
        });
    }
    
    @Override
    public void attendanceUpdated(AttendanceRecord record) {
        SwingUtilities.invokeLater(() -> {
            attendanceErrorLabel.setText("Attendance updated for: " + record.getStudentId());
        });
    }
    
    @Override
    public void showAttendanceByDate(List<AttendanceRecord> records) {
        SwingUtilities.invokeLater(() -> {
            StringBuilder sb = new StringBuilder();
            for (AttendanceRecord record : records) {
                String status = record.isPresent() ? "Present" : "Absent";
                sb.append(record.getDate()).append(": Student ").append(record.getStudentId())
                  .append(" - ").append(status).append("\n");
            }
            attendanceRecordsArea.setText(sb.toString());
            attendanceErrorLabel.setText("Showing " + records.size() + " records by date");
        });
    }
    
    @Override
    public void showAttendanceByStudent(List<AttendanceRecord> records) {
        SwingUtilities.invokeLater(() -> {
            StringBuilder sb = new StringBuilder();
            for (AttendanceRecord record : records) {
                String status = record.isPresent() ? "Present" : "Absent";
                sb.append("Date: ").append(record.getDate())
                  .append(" - ").append(status).append("\n");
            }
            attendanceRecordsArea.setText(sb.toString());
            attendanceErrorLabel.setText("Showing attendance for selected student");
        });
    }
    
    @Override
    public void showAttendancePercentage(double percentage) {
        SwingUtilities.invokeLater(() -> {
            summaryLabel.setText(String.format("Overall Attendance: %.1f%%", percentage));
        });
    }
    
    @Override
    public void showAttendanceError(String message) {
        SwingUtilities.invokeLater(() -> {
            attendanceErrorLabel.setText("Error: " + message);
        });
    }
    
    // Helper method for testing
    public JList getStudentList() {
        return liststudent;
    }
    
    private void addStudentAction() {
        String name = textFieldname.getText().trim();
        String rollNumber = textFieldrollno.getText().trim();
        
        if (name.isEmpty() || rollNumber.isEmpty()) {
            showStudentError("Name and Roll Number are required", null);
            return;
        }
        
        if (studentController == null) {
            showStudentError("Database not connected", null);
            return;
        }
        
        try {
            Student student = studentController.addStudent(name, rollNumber);
            studentAdded(student); // This updates UI via interface method
            textFieldname.setText("");
            textFieldrollno.setText("");
            loadStudentsFromDatabase(); // Refresh the list with new student
            refreshAttendancePanelStudents(); // Also refresh attendance panel
        } catch (IllegalArgumentException ex) {
            showStudentError(ex.getMessage(), null);
        } catch (Exception ex) {
            showStudentError("Failed to add student: " + ex.getMessage(), null);
        }
    }
    
    public void setStudentController(StudentController studentController) {
        this.studentController = studentController;
        loadStudentsFromDatabase(); // LOAD STUDENTS WHEN CONTROLLER IS SET
        refreshAttendancePanelStudents(); // Also refresh attendance panel
    }
    
    public void setAttendanceController(AttendanceController attendanceController) {
        this.attendanceController = attendanceController;
    }
    
    private void loadStudentsFromDatabase() {
        if (studentController != null) {
            try {
                List<Student> students = studentController.getAllStudents();
                // Convert to array for JList
                String[] studentArray = new String[students.size()];
                for (int i = 0; i < students.size(); i++) {
                    Student s = students.get(i);
                    studentArray[i] = s.getRollNumber() + " - " + s.getName();
                }
                
                liststudent.setListData(studentArray);
                lblerror.setText("Loaded " + students.size() + " students from database");
            } catch (Exception e) {
                showStudentError("Failed to load students: " + e.getMessage(), null);
            }
        }
    }
    
    private void deleteSelectedStudent() {
        String selected = (String) liststudent.getSelectedValue();
        if (selected == null) {
            showStudentError("Please select a student to delete", null);
            return;
        }
        
        try {
            String rollNumber = selected.split(" - ")[0];
            
            java.util.Optional<Student> studentOpt = studentController.getStudentByRollNumber(rollNumber);
            
            if (studentOpt.isPresent()) {
                Student student = studentOpt.get();
                studentController.deleteStudent(rollNumber);
                studentDeleted(student); // Pass the actual student object
                loadStudentsFromDatabase(); // Refresh the list
                refreshAttendancePanelStudents(); // Also refresh attendance panel
            } else {
                showStudentError("Student not found: " + rollNumber, null);
            }
        } catch (Exception ex) {
            showStudentError("Failed to delete student: " + ex.getMessage(), null);
        }
    }
    
    private void updateSelectedStudent() {
        String selected = (String) liststudent.getSelectedValue();
        if (selected == null) {
            showStudentError("Please select a student to update", null);
            return;
        }
        
        String oldRollNumber = selected.split(" - ")[0];
        String newName = textFieldname.getText().trim();
        String newRollNumber = textFieldrollno.getText().trim();
        
        if (newName.isEmpty() || newRollNumber.isEmpty()) {
            showStudentError("Enter new name and roll number", null);
            return;
        }
        
        try {
            Student updatedStudent = studentController.updateStudent(oldRollNumber, newName, newRollNumber);
            studentUpdated(updatedStudent);
            loadStudentsFromDatabase(); // Refresh list
            refreshAttendancePanelStudents(); // Also refresh attendance panel
            textFieldname.setText("");
            textFieldrollno.setText("");
        } catch (Exception ex) {
            showStudentError("Failed to update student: " + ex.getMessage(), null);
        }
    }
}