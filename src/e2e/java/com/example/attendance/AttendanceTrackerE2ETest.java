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
        window.textBox("studentnameTextBox").enterText("Haider");
        window.textBox("rollnumberTxtBox").enterText("123");
        window.button("addButton").click();
        window.robot().waitForIdle();
        window.label("errorLabel").requireText("Student added: Haider");
    }
}