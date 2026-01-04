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
import java.awt.event.ActionEvent;

public class AttendanceTrackerSwingView extends JFrame {

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
        
        // Buttons
        btnAdd = new JButton("Add");
        btnAdd.setName("addButton"); // Add component name for testing
        btnAdd.setBounds(20, 120, 120, 25);
        panel.add(btnAdd);
        
        btnUpdate = new JButton("Update");
        btnUpdate.setName("updateButton"); // Add component name for testing
        btnUpdate.setBounds(150, 120, 120, 25);
        panel.add(btnUpdate);
        
        btnDelete = new JButton("Delete");
        btnDelete.setName("deleteButton"); // Add component name for testing
        btnDelete.setBounds(280, 120, 120, 25);
        panel.add(btnDelete);
        
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
        
        // Create a panel to hold student attendance controls
        JPanel studentsPanel = new JPanel();
        studentsPanel.setLayout(null);
        studentsPanel.setBounds(20, 120, 350, 150);
        studentsPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        
        
        // Student 1 Junaid
        JLabel student1Label = new JLabel("Junaid (7131056)");
        student1Label.setBounds(10, 10, 150, 20);
        studentsPanel.add(student1Label);
        
        JRadioButton student1Present = new JRadioButton("Present");
        student1Present.setBounds(170, 10, 70, 20);
        studentsPanel.add(student1Present);
        
        JRadioButton student1Absent = new JRadioButton("Absent");
        student1Absent.setBounds(250, 10, 70, 20);
        studentsPanel.add(student1Absent);
        
        // Group radio buttons for student 1
        ButtonGroup group1 = new ButtonGroup();
        group1.add(student1Present);
        group1.add(student1Absent);
        student1Present.setSelected(true);
        
        // Student 2 awais
        JLabel student2Label = new JLabel("Awais (7131057)");
        student2Label.setBounds(10, 40, 150, 20);
        studentsPanel.add(student2Label);
        
        JRadioButton student2Present = new JRadioButton("Present");
        student2Present.setBounds(170, 40, 70, 20);
        studentsPanel.add(student2Present);
        
        JRadioButton student2Absent = new JRadioButton("Absent");
        student2Absent.setBounds(250, 40, 70, 20);
        studentsPanel.add(student2Absent);
        
        // Group radio buttons for student 2
        ButtonGroup group2 = new ButtonGroup();
        group2.add(student2Present);
        group2.add(student2Absent);
        student2Absent.setSelected(true);
        
        // Student 3 shayan
        JLabel student3Label = new JLabel("Shayan (7131058)");
        student3Label.setBounds(10, 70, 150, 20);
        studentsPanel.add(student3Label);
        
        JRadioButton student3Present = new JRadioButton("Present");
        student3Present.setBounds(170, 70, 70, 20);
        studentsPanel.add(student3Present);
        
        JRadioButton student3Absent = new JRadioButton("Absent");
        student3Absent.setBounds(250, 70, 70, 20);
        studentsPanel.add(student3Absent);
        
        // Group radio buttons for student 3
        ButtonGroup group3 = new ButtonGroup();
        group3.add(student3Present);
        group3.add(student3Absent);
        
        panel.add(studentsPanel);
        
        // Mark Attendance button
        btnmarkAttendance = new JButton("Mark Attendance");
        btnmarkAttendance.setName("markAttendanceButton"); // Add component name for testing
        btnmarkAttendance.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
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
        attendanceRecordsArea.setText("2026-04-01: Junaid - Present\r\n2026-04-01: Awais - Absent");
        attendanceRecordsArea.setEditable(false);
        
        // Summary label
        summaryLabel = new JLabel("Attendance: 75%");
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
}