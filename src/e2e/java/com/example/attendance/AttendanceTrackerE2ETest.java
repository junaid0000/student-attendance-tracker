package com.example.attendance;

import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.attendance.view.swing.AttendanceTrackerSwingView;

@RunWith(GUITestRunner.class)
public class AttendanceTrackerE2ETest extends AssertJSwingJUnitTestCase {

    private FrameFixture window;

    @Override
    protected void onSetUp() {
        AttendanceTrackerSwingView view = GuiActionRunner.execute(() -> {
            return new AttendanceTrackerSwingView();
        });
        
        window = new FrameFixture(robot(), view);
        window.show(); // Make window visible
    }

    @Override
    protected void onTearDown() {
        //Clean up resources 
        if (window != null) {
            window.cleanUp();
        }
    }
    
    @Test
    public void testApplicationStartsSuccessfully() {
        window.requireVisible();
        window.requireTitle("Student Attendance Tracker");
    }
    @Test
    public void testStudentTabComponentsExist() {
        window.tabbedPane().selectTab("Students");
        window.robot().waitForIdle();
        window.textBox("studentnameTextBox").requireVisible();
        window.textBox("rollnumberTxtBox").requireVisible();
        window.button("addButton").requireVisible();
        window.list("studentlist").requireVisible();
        window.label("errorLabel").requireVisible();
    }
    @Test
    public void testAddStudentFunctionality() {
        window.tabbedPane().selectTab("Students");
        com.example.attendance.model.Student student = new com.example.attendance.model.Student("Haider", "123");
        AttendanceTrackerSwingView view = (AttendanceTrackerSwingView) window.target();
        view.studentAdded(student);
        window.label("errorLabel").requireText("Student added: Haider");
    }
    @Test
    public void testAttendanceTabFunctionality() {
        window.tabbedPane().selectTab("Attendance");
        window.robot().waitForIdle();
        
        com.example.attendance.model.AttendanceRecord record = 
            new com.example.attendance.model.AttendanceRecord("7131056", new java.util.Date(), true);
        
        AttendanceTrackerSwingView view = (AttendanceTrackerSwingView) window.target();
        view.attendanceMarked(record);
        
        window.label("attendanceErrorLabel").requireText("Attendance marked for student ID  7131056");
    }
}