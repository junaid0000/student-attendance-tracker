package com.example.attendance.view.swing;

import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.awt.GraphicsEnvironment;

import com.example.attendance.model.Student;
import com.example.attendance.model.AttendanceRecord;
import java.util.Date;

@RunWith(GUITestRunner.class)
public class AttendanceTrackerSwingViewTest extends AssertJSwingJUnitTestCase {

    private FrameFixture window;
    private AttendanceTrackerSwingView view;

    @Override
    protected void onSetUp() {
        if (GraphicsEnvironment.isHeadless()) return;
        view = GuiActionRunner.execute(() -> new AttendanceTrackerSwingView());
        window = new FrameFixture(robot(), view);
        window.show();
    }

    //GUI TESTS
    
    @Test
    public void testViewIsVisible() {
        if (GraphicsEnvironment.isHeadless()) return;
        window.requireVisible();
    }
    
    @Test
    public void testAllComponentsExist() {
        if (GraphicsEnvironment.isHeadless()) return;
        
        // Students tab 
        window.tabbedPane().selectTab("Students");
        window.textBox("studentnameTextBox").requireVisible();
        window.textBox("rollnumberTxtBox").requireVisible();
        window.button("addButton").requireVisible();
        window.button("updateButton").requireVisible();
        window.button("deleteButton").requireVisible();
        window.list("studentlist").requireVisible();
        window.label("errorLabel").requireVisible();
        
        // Attendance tab
        window.tabbedPane().selectTab("Attendance");
        window.textBox("dateTextField").requireVisible();
        window.button("markAttendanceButton").requireVisible();
        window.button("viewByDateButton").requireVisible();
        window.button("getSummaryButton").requireVisible();
        window.textBox("attendanceRecordsArea").requireVisible();
        window.label("summaryLabel").requireVisible();
        window.label("attendanceErrorLabel").requireVisible();
    }
    
    @Test
    public void testAddButtonStateManagement() {
        if (GraphicsEnvironment.isHeadless()) return;
        
        window.tabbedPane().selectTab("Students");
        window.button("addButton").requireDisabled();
        
        window.textBox("studentnameTextBox").enterText("John");
        window.textBox("rollnumberTxtBox").enterText("123");
        window.button("addButton").requireEnabled();
    }

    //INTERFACE IMPLEMENTATION tests
    
    @Test
    public void testStudentAddedInterfaceMethod() {
        if (GraphicsEnvironment.isHeadless()) return;
        
        Student student = new Student("Ahmed", "123");
        view.studentAdded(student);
        window.label("errorLabel").requireText("Student added: Ahmed");
    }
    
    @Test
    public void testStudentUpdatedInterfaceMethod() {
        if (GraphicsEnvironment.isHeadless()) return;
        
        Student student = new Student("Umer", "456");
        view.studentUpdated(student);
        window.label("errorLabel").requireText("Student updated: Umer");
    }
    
    @Test
    public void testStudentDeletedInterfaceMethod() {
        if (GraphicsEnvironment.isHeadless()) return;
        
        Student student = new Student("Sarim", "789");
        view.studentDeleted(student);
        window.label("errorLabel").requireText("Student deleted: Sarim");
    }
    
    @Test
    public void testShowStudentErrorInterfaceMethod() {
        if (GraphicsEnvironment.isHeadless()) return;
        
        Student student = new Student("Test", "999");
        view.showStudentError("Test error", student);
        window.label("errorLabel").requireText("Error: Test error");
    }
    
    @Test
    public void testAttendanceMarkedInterfaceMethod() {
        if (GraphicsEnvironment.isHeadless()) return;
        
        window.tabbedPane().selectTab("Attendance");
        AttendanceRecord record = new AttendanceRecord("123", new Date(), true);
        view.attendanceMarked(record);
        window.label("attendanceErrorLabel").requireText("Attendance marked for student ID: 123");
    }
    
    @Test
    public void testShowAttendancePercentageInterfaceMethod() {
        if (GraphicsEnvironment.isHeadless()) return;
        
        window.tabbedPane().selectTab("Attendance");
        view.showAttendancePercentage(85.5);
        window.label("summaryLabel").requireText("Overall Attendance: 85.5%");
    }
}