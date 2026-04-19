package com.example.attendance.view.swing;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import com.example.attendance.controller.AttendanceController;
import com.example.attendance.controller.StudentController;
import com.example.attendance.model.AttendanceRecord;
import com.example.attendance.model.Student;
import com.example.attendance.view.AttendanceTrackerView;

public class AttendanceTrackerSwingView extends JFrame implements AttendanceTrackerView {

    private static final long serialVersionUID = 1L;
    private static final String FONT_TAHOMA = "Tahoma";
    private static final String FONT_MONOSPACED = "Monospaced";
    private static final String ERROR_PREFIX = "Error: ";
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String STATUS_PRESENT = "Present";
    private static final String STATUS_ABSENT = "Absent";
    private static final String LOADED_MSG = "Loaded ";
    private static final String LOADED_SUFFIX = " students from database";
    private static final String TAB_STUDENTS = "Students";
    private static final String ATTENDANCE_DB = "attendance_db";
    private static final String SEPARATOR = " - ";
    private static final Logger LOGGER = Logger.getLogger(AttendanceTrackerSwingView.class.getName());
    private JTabbedPane tabbedPane;
    private boolean isTestMode = false;

    // Students Tab Components
    private JTextField textFieldName;
    private JTextField textFieldRollNo;
    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JList<String> listStudent;
    private JLabel lblError;

    // Attendance Tab Components
    private JTextField dateFieldAttendance;
    private JButton btnMarkAttendance;
    private JButton btnViewByDate;
    private JButton btnGetSummary;
    private JRadioButton byDateRadio;
    private JRadioButton byStudentRadio;
    private JList<String> attendanceList;
    private JTextArea attendanceRecordsArea;
    private JLabel attendanceErrorLabel;
    private JLabel summaryLabel;
    private StudentController studentController;
    private AttendanceController attendanceController;

    // Attendance panel components
    private JPanel studentsAttendancePanel;
    private Map<String, ButtonGroup> studentAttendanceGroups;
    private List<Student> currentStudentsList;
    // it will showing the success message
    private boolean showingSuccessMessage = false;
    private boolean showingErrorMessage = false;
    private int currentMessageId = 0;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                AttendanceTrackerSwingView frame = new AttendanceTrackerSwingView();
                frame.setVisible(true);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Exception occurred while starting AttendanceTrackerSwingView", e);
            }
        });
    }

    public AttendanceTrackerSwingView() {
        // QUICKLY CHECK TEST MODE BEFORE UI INIT
        if (Boolean.parseBoolean(System.getProperty("test.mode", "false"))) {
            this.isTestMode = true;
        }

        setTitle("Student Attendance Tracker");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);

        // Create the tabbed pane
        tabbedPane = new JTabbedPane();
        tabbedPane.setName("tabbedPane");

        // Create Students Tab
        JPanel studentsPanel = createStudentsPanel();
        tabbedPane.addTab(TAB_STUDENTS, studentsPanel);

        // Create Attendance Tab
        JPanel attendancePanel = createAttendancePanel();
        tabbedPane.addTab("Attendance", attendancePanel);

        // Add title at top
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Student Attendance Tracker");
        titleLabel.setFont(new Font(FONT_TAHOMA, Font.BOLD, 16));
        titlePanel.add(titleLabel);

        // Add everything to frame
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(titlePanel, BorderLayout.NORTH);
        getContentPane().add(tabbedPane, BorderLayout.CENTER);

        // Center the window on screen for reliable test robot interactions
        setLocationRelativeTo(null);

        // Initialize collections
        studentAttendanceGroups = new HashMap<>();
        currentStudentsList = new ArrayList<>();

        // ok this is Note: Students will be loaded when setStudentController is called
    }

    // SETTER METHOD FOR TEST MODE
    public void setTestMode(boolean testMode) {
        this.isTestMode = testMode;
    }

    private JPanel createStudentsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Title for Students tab
        JTextArea txtStudentsTab = new JTextArea();
        txtStudentsTab.setFont(new Font(FONT_MONOSPACED, Font.BOLD, 13));
        txtStudentsTab.setText("============== STUDENTS TAB ==============");
        txtStudentsTab.setBounds(190, 11, 400, 20);
        txtStudentsTab.setEditable(false);
        txtStudentsTab.setOpaque(false);
        panel.add(txtStudentsTab);

        // Name label and field
        JLabel lblName = new JLabel("Name:");
        lblName.setFont(new Font(FONT_TAHOMA, Font.BOLD, 11));
        lblName.setHorizontalAlignment(SwingConstants.RIGHT);
        lblName.setBounds(20, 51, 60, 20);
        panel.add(lblName);

        textFieldName = new JTextField();
        textFieldName.setName("studentnameTextBox"); // so now do not change this name it is used in tests
        textFieldName.setBounds(90, 50, 200, 20);
        panel.add(textFieldName);
        textFieldName.setColumns(10);
        // ADD KEY LISTENER
        textFieldName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                updateAddButtonState();
            }
        });

        // Roll No label and field
        JLabel lblRollNo = new JLabel("Roll No:");
        lblRollNo.setFont(new Font(FONT_TAHOMA, Font.BOLD, 11));
        lblRollNo.setHorizontalAlignment(SwingConstants.RIGHT);
        lblRollNo.setBounds(20, 80, 60, 20);
        panel.add(lblRollNo);

        textFieldRollNo = new JTextField();
        textFieldRollNo.setName("rollnumberTxtBox"); // so now do not change this name it is used in tests
        textFieldRollNo.setBounds(90, 80, 200, 20);
        panel.add(textFieldRollNo);
        textFieldRollNo.setColumns(10);
        // ADD KEY LISTENER
        textFieldRollNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                updateAddButtonState();
            }
        });

        // Buttons
        btnAdd = new JButton("Add");
        btnAdd.setName("addButton"); // Add component name for testing
        btnAdd.setBounds(20, 120, 120, 25);
        btnAdd.setEnabled(false); // btn disable
        panel.add(btnAdd);

        // Adding LISTENER
        btnAdd.addActionListener(e -> addStudentAction());

        btnUpdate = new JButton("Update");
        btnUpdate.setName("updateButton"); // Add component name for testing
        btnUpdate.setBounds(150, 120, 120, 25);
        panel.add(btnUpdate);

        btnUpdate.addActionListener(e -> updateSelectedStudent());

        btnDelete = new JButton("Delete");
        btnDelete.setName("deleteButton"); // Add component name for testing
        btnDelete.setBounds(280, 120, 120, 25);
        panel.add(btnDelete);

        btnDelete.addActionListener(e -> deleteSelectedStudent());

        // Student List label
        JTextArea txtStudentList = new JTextArea();
        txtStudentList.setFont(new Font(FONT_MONOSPACED, Font.BOLD, 13));
        txtStudentList.setText("Student List:");
        txtStudentList.setBounds(20, 160, 150, 20);
        txtStudentList.setEditable(false);
        txtStudentList.setOpaque(false);
        panel.add(txtStudentList);

        // Student List with scroll
        JScrollPane studentScrollPane = new JScrollPane();
        studentScrollPane.setBounds(20, 180, 270, 150);
        panel.add(studentScrollPane);

        listStudent = new JList<>();
        listStudent.setName("studentlist");
        studentScrollPane.setViewportView(listStudent);

        // Error label
        lblError = new JLabel("No errors");
        lblError.setName("errorLabel"); // Add component name for testing
        lblError.setFont(new Font(FONT_TAHOMA, Font.BOLD, 11));
        lblError.setHorizontalAlignment(SwingConstants.LEFT);
        lblError.setBounds(20, 400, 350, 20);
        panel.add(lblError);

        return panel;
    }

    private void updateAddButtonState() {
        boolean nameNotEmpty = !textFieldName.getText().trim().isEmpty();
        boolean rollNoNotEmpty = !textFieldRollNo.getText().trim().isEmpty();
        btnAdd.setEnabled(nameNotEmpty && rollNoNotEmpty);
    }

    private JPanel createAttendancePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Title for Attendance tab
        JTextArea txtAttendanceTab = new JTextArea();
        txtAttendanceTab.setText("============== ATTENDANCE TAB ==============");
        txtAttendanceTab.setFont(new Font(FONT_MONOSPACED, Font.BOLD, 13));
        txtAttendanceTab.setBounds(190, 11, 400, 20);
        txtAttendanceTab.setEditable(false);
        txtAttendanceTab.setOpaque(false);
        panel.add(txtAttendanceTab);

        // Date section
        JLabel dateLabel = new JLabel("Date:");
        dateLabel.setFont(new Font(FONT_TAHOMA, Font.PLAIN, 12));
        dateLabel.setBounds(20, 50, 50, 20);
        panel.add(dateLabel);

        dateFieldAttendance = new JTextField();
        dateFieldAttendance.setName("dateTextField"); // Add component name for testing
        dateFieldAttendance.setText(DATE_FORMAT);
        dateFieldAttendance.setBounds(70, 50, 120, 20);
        panel.add(dateFieldAttendance);

        // Students attendance area title
        JLabel studentsLabel = new JLabel("Students for Attendance:");
        studentsLabel.setFont(new Font(FONT_TAHOMA, Font.BOLD, 10));
        studentsLabel.setBounds(20, 90, 180, 20);
        panel.add(studentsLabel);

        // Create a panel to hold student attendance controls.
        studentsAttendancePanel = new JPanel();
        studentsAttendancePanel.setLayout(new BoxLayout(studentsAttendancePanel, BoxLayout.Y_AXIS));

        JScrollPane attendanceScrollPane = new JScrollPane(studentsAttendancePanel);
        attendanceScrollPane.setBounds(20, 120, 350, 150);
        attendanceScrollPane.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel.add(attendanceScrollPane);

        // Mark Attendance button
        btnMarkAttendance = new JButton("Mark Attendance");
        btnMarkAttendance.setName("markAttendanceButton"); // Add component name for testing
        btnMarkAttendance.addActionListener(e -> markAttendanceAction());
        btnMarkAttendance.setFont(new Font(FONT_TAHOMA, Font.PLAIN, 11));
        btnMarkAttendance.setBounds(20, 290, 150, 30);
        panel.add(btnMarkAttendance);

        // View Attendance section
        JLabel viewLabel = new JLabel("View Attendance:");
        viewLabel.setFont(new Font(FONT_TAHOMA, Font.BOLD, 10));
        viewLabel.setBounds(20, 340, 120, 20);
        panel.add(viewLabel);

        byDateRadio = new JRadioButton("By Date");
        byDateRadio.setSelected(true);
        byDateRadio.setBounds(140, 340, 80, 20);
        panel.add(byDateRadio);

        byStudentRadio = new JRadioButton("By Student");
        byStudentRadio.setBounds(220, 340, 100, 20);
        panel.add(byStudentRadio);

        // Group the view radio buttons
        ButtonGroup viewGroup = new ButtonGroup();
        viewGroup.add(byDateRadio);
        viewGroup.add(byStudentRadio);

        // View Attendance button
        btnViewByDate = new JButton("View Attendance");
        btnViewByDate.setName("viewByDateButton"); // Add compnent name for testing
        btnViewByDate.setFont(new Font(FONT_TAHOMA, Font.PLAIN, 11));
        btnViewByDate.setBounds(20, 370, 150, 30);
        btnViewByDate.addActionListener(e -> viewAttendanceAction());
        panel.add(btnViewByDate);

        // Get Summary button
        btnGetSummary = new JButton("Get Summary");
        btnGetSummary.setName("getSummaryButton"); // Add component name for testing
        btnGetSummary.setFont(new Font(FONT_TAHOMA, Font.PLAIN, 11));
        btnGetSummary.setBounds(180, 370, 150, 30);
        btnGetSummary.addActionListener(e -> getSummaryAction());
        panel.add(btnGetSummary);

        // Attendance records display
        JLabel recordsLabel = new JLabel("Attendance Records:");
        recordsLabel.setFont(new Font(FONT_TAHOMA, Font.BOLD, 12));
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
        summaryLabel.setFont(new Font(FONT_TAHOMA, Font.BOLD, 14));
        summaryLabel.setBounds(20, 540, 200, 30);
        panel.add(summaryLabel);

        // Error label
        attendanceErrorLabel = new JLabel("");
        attendanceErrorLabel.setName("attendanceErrorLabel"); // Add compnent name for testing
        attendanceErrorLabel.setFont(new Font(FONT_TAHOMA, Font.PLAIN, 11));
        attendanceErrorLabel.setForeground(java.awt.Color.RED);
        attendanceErrorLabel.setBounds(20, 570, 450, 20);
        panel.add(attendanceErrorLabel);

        return panel;
    }

    // Refresh attendance panel with students from database
    private void refreshAttendancePanelStudents() {
        if (studentController == null)
            return;

        studentsAttendancePanel.removeAll();
        studentAttendanceGroups.clear();
        currentStudentsList.clear();

        try {
            List<Student> students = studentController.getAllStudents();
            if (students == null) {
                return; // Guard against mock returning null
            }

            currentStudentsList = students;

            for (Student student : students) {
                JPanel studentPanel = new JPanel();
                studentPanel.setLayout(null);
                studentPanel.setPreferredSize(new java.awt.Dimension(330, 30));

                JLabel studentLabel = new JLabel(student.getName() + " (" + student.getRollNumber() + ")");
                studentLabel.setBounds(10, 5, 150, 20);
                studentPanel.add(studentLabel);

                JRadioButton presentRadio = new JRadioButton(STATUS_PRESENT);
                presentRadio.setActionCommand(STATUS_PRESENT);
                presentRadio.setBounds(170, 5, 70, 20);
                studentPanel.add(presentRadio);

                JRadioButton absentRadio = new JRadioButton(STATUS_ABSENT);
                absentRadio.setActionCommand(STATUS_ABSENT);
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

            if (!isTestMode) {
                studentsAttendancePanel.revalidate();
                studentsAttendancePanel.repaint();
            }

        } catch (Exception e) {
            attendanceErrorLabel.setText("Error loading students: " + e.getMessage());
        }
    }

    /**
     * Parses the date from the date field. Falls back to the current date if empty
     * or invalid.
     * 
     * @return java.util.Date
     */
    private java.util.Date parseDate() {
        try {
            String dateTxt = dateFieldAttendance.getText();
            if (dateTxt.equals(DATE_FORMAT) || dateTxt.isEmpty()) {
                return new java.util.Date();
            }
            return new SimpleDateFormat(DATE_FORMAT).parse(dateTxt);
        } catch (Exception e) {
            return new java.util.Date(); // Fallback to current date
        }
    }

    /**
     * Action performed when clicking the 'View Attendance' button. Supports
     * filtering by date (from the date field) or by student (via input dialog).
     */
    private void viewAttendanceAction() {
        if (attendanceController == null)
            return;
        try {
            if (byDateRadio.isSelected()) {
                // Filter records by the date specified in the UI
                List<AttendanceRecord> records = attendanceController.getAttendanceByDate(parseDate());
                showAttendanceByDate(records);
            } else if (byStudentRadio.isSelected()) {
                // Prompt the user for a roll number and filter records by that student
                String rollNo = JOptionPane.showInputDialog(this, "Enter Student Roll Number:");
                if (rollNo != null && !rollNo.trim().isEmpty()) {
                    List<AttendanceRecord> records = attendanceController.getAttendanceByStudent(rollNo);
                    showAttendanceByStudent(records);
                }
            }
        } catch (Exception e) {
            showAttendanceError(e.getMessage());
        }
    }

    /**
     * Action performed when clicking the 'Get Summary' button. Calculates and
     * displays the attendance percentage for a specific student.
     */
    private void getSummaryAction() {
        if (attendanceController == null)
            return;
        try {
            // ask the user for a roll number to calculate the cumulative attendance
            // percentage
            String rollNo = JOptionPane.showInputDialog(this, "Enter Student Roll Number for Summary:");
            if (rollNo != null && !rollNo.trim().isEmpty()) {
                double perc = attendanceController.getAttendancePercentage(rollNo);
                showAttendancePercentage(perc);
            }
        } catch (Exception e) {
            showAttendanceError(e.getMessage());
        }
    }

    // Mark attendance action
    private void markAttendanceAction() {
        if (attendanceController == null) {
            attendanceErrorLabel.setText("Attendance controller not set");
            return;
        }

        // Use provided date
        java.util.Date date = parseDate();

        boolean anyMarked = false;
        int markedCount = 0;

        // Mark attendance for each student
        for (Student student : currentStudentsList) {
            try {
                ButtonGroup group = studentAttendanceGroups.get(student.getRollNumber());
                boolean isPresent = true;
                if (group != null && group.getSelection() != null) {
                    isPresent = group.getSelection().getActionCommand().equals(STATUS_PRESENT);
                }
                AttendanceRecord attendanceRecord = attendanceController.markAttendance(student.getRollNumber(), date, isPresent);
                markedCount++;
                anyMarked = true;
            } catch (Exception e) {
                attendanceErrorLabel.setText(ERROR_PREFIX + e.getMessage());
                return;
            }
        }

        if (anyMarked) {
            // Update the status label and show a confirmation popup (only if not in test
            // mode)
            attendanceErrorLabel.setText("Attendance marked for " + markedCount + " student(s)");
            if (!isTestMode) {
                JOptionPane.showMessageDialog(this, "Attendance marked successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            }

            // Trigger UI update through the attendanceMarked listener
            AttendanceRecord dummyRecord = new AttendanceRecord("temp", date, true, "temp");
            attendanceMarked(dummyRecord);
        } else {
            // Failure: Notify the user if no students were found to mark
            attendanceErrorLabel.setText("No students to mark attendance");
        }
    }

    // Interface Implementation

    @Override
    public void studentAdded(Student student) {
        SwingUtilities.invokeLater(() -> {
            lblError.setText("Student added: " + student.getName());
            loadStudentsFromDatabase();
            refreshAttendancePanelStudents();
        });
    }

    @Override
    public void studentUpdated(Student student) {
        SwingUtilities.invokeLater(() -> {
            lblError.setText("Student updated: " + student.getName());
            loadStudentsFromDatabase();
            refreshAttendancePanelStudents();
        });
    }

    @Override
    public void studentDeleted(Student student) {
        SwingUtilities.invokeLater(() -> {
            String message = (student != null) ? "Student deleted: " + student.getName()
                    : "Student deleted successfully";

            lblError.setText(message);
            loadStudentsFromDatabase();
            refreshAttendancePanelStudents();
        });
    }

    @Override
    public void showStudentError(String message, Student student) {
        final int msgId = ++currentMessageId;
        Runnable r = () -> {
            showingSuccessMessage = false;
            showingErrorMessage = true;
            lblError.setText(ERROR_PREFIX + message);
        };
        if (SwingUtilities.isEventDispatchThread()) {
            r.run();
        } else {
            SwingUtilities.invokeLater(r);
        }
    }

    @Override
    public void attendanceMarked(AttendanceRecord attendanceRecord) {
        SwingUtilities.invokeLater(() -> {
            attendanceErrorLabel.setText("Attendance marked for student ID: " + attendanceRecord.getStudentId());
        });
    }

    @Override
    public void attendanceUpdated(AttendanceRecord attendanceRecord) {
        SwingUtilities.invokeLater(() -> {
            attendanceErrorLabel.setText("Attendance updated for: " + attendanceRecord.getStudentId());
        });
    }

    /**
     * Retrieves student name and roll number for display in attendance records
     * based on student ID.
     */
    private String getStudentDetails(String studentId) {
        if (studentController != null) {
            try {
                for (Student s : studentController.getAllStudents()) {
                    if (s.getStudentId().equals(studentId)) {
                        return s.getName() + " (" + s.getRollNumber() + ")";
                    }
                }
            } catch (Exception e) {
                // Ignore exception to fallback to printing ID
            }
        }
        return "ID: " + studentId;
    }

    @Override
    public void showAttendanceByDate(List<AttendanceRecord> records) {
        SwingUtilities.invokeLater(() -> {
            StringBuilder sb = new StringBuilder();
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            for (AttendanceRecord attendanceRecord : records) {
                String status = attendanceRecord.isPresent() ? STATUS_PRESENT : STATUS_ABSENT;
                String studentDetails = getStudentDetails(attendanceRecord.getStudentId());
                sb.append("Date: ").append(sdf.format(attendanceRecord.getDate())).append(" | ").append(studentDetails)
                        .append(SEPARATOR).append(status).append("\n");
            }
            attendanceRecordsArea.setText(sb.toString());
            attendanceErrorLabel.setText(LOADED_MSG + records.size() + " records by date");
        });
    }

    @Override
    public void showAttendanceByStudent(List<AttendanceRecord> records) {
        SwingUtilities.invokeLater(() -> {
            StringBuilder sb = new StringBuilder();
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            for (AttendanceRecord attendanceRecord : records) {
                String status = attendanceRecord.isPresent() ? STATUS_PRESENT : STATUS_ABSENT;
                String studentDetails = getStudentDetails(attendanceRecord.getStudentId());
                sb.append("Date: ").append(sdf.format(attendanceRecord.getDate())).append(" | ").append(studentDetails)
                        .append(SEPARATOR).append(status).append("\n");
            }
            attendanceRecordsArea.setText(sb.toString());
            attendanceErrorLabel.setText(LOADED_MSG + "attendance for selected student");
        });
    }

    @Override
    public void showAttendancePercentage(double percentage) {
        SwingUtilities.invokeLater(() -> {
            String formatted = String.format("Overall Attendance: %.1f%%", percentage);
            summaryLabel.setText(formatted);
            if (!isTestMode) {
                JOptionPane.showMessageDialog(this, formatted, "Attendance Summary",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    @Override
    public void showAttendanceError(String message) {
        SwingUtilities.invokeLater(() -> {
            attendanceErrorLabel.setText(ERROR_PREFIX + message);
        });
    }

    // Helper method for testing
    public JList<String> getStudentList() {
        return listStudent;
    }

    private void displaySuccessMessage(String message) {
        final int msgId = ++currentMessageId;
        showingErrorMessage = false;
        showingSuccessMessage = true;
        lblError.setText(message);

        resetStatusAfterDelay(msgId);
    }

    private void addStudentAction() {
        // Reset flags at the start of action
        showingSuccessMessage = false;
        showingErrorMessage = false;
        final int msgId = ++currentMessageId;

        String name = textFieldName.getText().trim();
        String rollNumber = textFieldRollNo.getText().trim();

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

            // Increment again after DB call to invalidate loads that started during the
            // call
            currentMessageId++;
            showingSuccessMessage = true;
            lblError.setText("Student added: " + student.getName());

            // Refresh the list with new student
            loadStudentsFromDatabase();
            refreshAttendancePanelStudents();

            textFieldName.setText("");
            textFieldRollNo.setText("");

            resetStatusAfterDelay(msgId);

        } catch (IllegalArgumentException ex) {
            currentMessageId++;
            showStudentError(ex.getMessage(), null);
        } catch (Exception ex) {
            currentMessageId++;
            showStudentError("Failed to add student: " + ex.getMessage(), null);
        }
    }

    public void setStudentController(StudentController studentController) {
        this.studentController = studentController;
        // ONLY LOAD IF NOT IN TEST MODE AND NOT NULL.
        // In test mode, we skip the initial load to keep the UI predictable for unit
        // tests.
        if (!isTestMode && studentController != null) {
            loadStudentsFromDatabase();
            refreshAttendancePanelStudents();
        }
    }

    public void setAttendanceController(AttendanceController attendanceController) {
        this.attendanceController = attendanceController;
    }

    private void loadStudentsFromDatabase() {
        if (studentController != null) {
            final int msgId = currentMessageId;
            try {
                List<Student> students = studentController.getAllStudents();
                if (students == null)
                    return;

                String[] studentArray = new String[students.size()];
                for (int i = 0; i < studentArray.length; i++) {
                    Student s = students.get(i);
                    studentArray[i] = s.getRollNumber() + SEPARATOR + s.getName();
                }

                // Store current selection to restore it after reload
                Object selected = listStudent.getSelectedValue();

                listStudent.setListData(studentArray);

                // Restore selection if it still exists
                if (selected != null) {
                    listStudent.setSelectedValue(selected, true);
                }

                // updated error label ONLY if it's currently showing a status/loaded message
                // AND no newer action has occurred
                String currentStatus = lblError.getText();
                boolean isStatusMsg = currentStatus.startsWith("Loaded") || currentStatus.equals("No errors")
                        || currentStatus.equals("Error: No errors") || currentStatus.isEmpty();

                if (isStatusMsg && !showingSuccessMessage && !showingErrorMessage && msgId == currentMessageId) {
                    lblError.setText(LOADED_MSG + students.size() + LOADED_SUFFIX);
                }
            } catch (Exception e) {
                showStudentError("Failed to load students: " + e.getMessage(), null);
            }
        }
    }

    private void deleteSelectedStudent() {
        final int msgId = ++currentMessageId;
        String selected = (String) listStudent.getSelectedValue();
        if (selected == null) {
            showStudentError("Please select a student to delete", null);
            return;
        }

        try {
            String rollNumber = selected.split(SEPARATOR)[0];

            java.util.Optional<Student> studentOpt = studentController.getStudentByRollNumber(rollNumber);

            if (studentOpt.isPresent()) {
                Student student = studentOpt.get();
                studentController.deleteStudent(rollNumber);
                
                showingSuccessMessage = true;
                lblError.setText("Student deleted: " + student.getName());
                
                loadStudentsFromDatabase(); // Refresh the list
                refreshAttendancePanelStudents(); // Also refresh attendance panel
                
                resetStatusAfterDelay(msgId);
            } else {
                showStudentError("Student not found: " + rollNumber, null);
            }
        } catch (Exception ex) {
            showStudentError("Failed to delete student: " + ex.getMessage(), null);
        }
    }

    private void updateSelectedStudent() {
        final int msgId = ++currentMessageId;
        String selected = (String) listStudent.getSelectedValue();
        if (selected == null) {
            showStudentError("Please select a student to update", null);
            return;
        }

        String oldRollNumber = selected.split(SEPARATOR)[0];
        String newName = textFieldName.getText().trim();
        String newRollNumber = textFieldRollNo.getText().trim();

        if (newName.isEmpty() || newRollNumber.isEmpty()) {
            showStudentError("Enter new name and roll number", null);
            return;
        }

        try {
            Student updatedStudent = studentController.updateStudent(oldRollNumber, newName, newRollNumber);
            loadStudentsFromDatabase(); // Refresh list
            refreshAttendancePanelStudents(); // Also refresh attendance panel
            textFieldName.setText("");
            textFieldRollNo.setText("");
        } catch (Exception ex) {
            showStudentError("Failed to update student: " + ex.getMessage(), null);
        }
    }

    private void resetStatusAfterDelay(int msgId) {
        // Reset flag after a delay. In test mode, we use a slightly shorter delay
        // but still long enough for tests to see the message (500ms).
        int delay = isTestMode ? 500 : 3000;
        new Timer(delay, e -> {
            // Only act if this is still the most recent message
            if (msgId == currentMessageId) {
                showingSuccessMessage = false;
                // Just update the label instead of full reload to avoid selection flickering
                if (studentController != null) {
                    try {
                        int count = studentController.getAllStudents().size();
                        // Only update if no error message has appeared in the meantime
                        if (!showingErrorMessage) {
                            lblError.setText(LOADED_MSG + count + LOADED_SUFFIX);
                        }
                    } catch (Exception ex) {
                        // Ignore exception during background list update
                    }
                }
            }
        }).start();
    }
}