package com.example.attendance.view.swing;

import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.awt.GraphicsEnvironment;
import static org.mockito.Mockito.*;

import com.example.attendance.model.Student;
import com.example.attendance.model.AttendanceRecord;
import com.example.attendance.controller.StudentController;  
import com.example.attendance.controller.AttendanceController;

import java.util.Date;

import javax.swing.SwingUtilities;

@RunWith(GUITestRunner.class)
public class AttendanceTrackerSwingViewTest extends AssertJSwingJUnitTestCase {
    static {
        System.setProperty("java.awt.headless", "false");// Fix for Java 17+ module access issues
        System.setProperty("test.mode", "true"); // Force test mode
    }
    private FrameFixture window;
    private AttendanceTrackerSwingView view;

    @Override
    protected void onSetUp() {
        if (GraphicsEnvironment.isHeadless()) return;
        
        // Create fake controllers that will not connect to database
        StudentController fakeStudentController = mock(StudentController.class);
        AttendanceController fakeAttendanceController = mock(AttendanceController.class);
        
        // CRITICAL: Set system property BEFORE creating view
        System.setProperty("test.mode", "true");
        
        view = GuiActionRunner.execute(() -> {
            AttendanceTrackerSwingView v = new AttendanceTrackerSwingView();
            v.setTestMode(true);
            return v;
        });
        
        // Set the fake controllers
        view.setStudentController(fakeStudentController);
        view.setAttendanceController(fakeAttendanceController);
        
        window = new FrameFixture(robot(), view);
        window.show(); // This should now work without MongoDB connection attempts
    }
    //GUI TESTS
    
    @Test
    public void testThatMainWindowIsVisibleWhenApplicationStarts() {
        if (GraphicsEnvironment.isHeadless()) return;
        window.requireVisible();
    }
    
    @Test
    public void testThatAllRequiredGUIComponentsArePresentAndVisible() {
        if (GraphicsEnvironment.isHeadless()) return;
        
        // Students tab  
        window.tabbedPane().selectTab("Students");
        window.robot().waitForIdle();
        window.textBox("studentnameTextBox").requireVisible();
        window.textBox("rollnumberTxtBox").requireVisible();
        window.button("addButton").requireVisible();
        window.button("updateButton").requireVisible();
        window.button("deleteButton").requireVisible();
        window.list("studentlist").requireVisible();
        window.label("errorLabel").requireVisible();
        
        // Attendance tab
        window.tabbedPane().selectTab("Attendance");
        window.robot().waitForIdle();
        window.textBox("dateTextField").requireVisible();
        window.button("markAttendanceButton").requireVisible();
        window.button("viewByDateButton").requireVisible();
        window.button("getSummaryButton").requireVisible();
        window.textBox("attendanceRecordsArea").requireVisible();
        window.label("summaryLabel").requireVisible();
        window.label("attendanceErrorLabel").requireVisible();
    }
    
    @Test
    public void testThatAddButtonIsDisabledWhenNameOrRollNumberIsEmpty() {
        if (GraphicsEnvironment.isHeadless()) return;
        
        window.tabbedPane().selectTab("Students");
        window.button("addButton").requireDisabled();
        
        window.textBox("studentnameTextBox").enterText("JJ");
        window.textBox("rollnumberTxtBox").enterText("123");
        window.button("addButton").requireEnabled();
    }

    //INTERFACE IMPLEMENTATION tests
    @Test
    public void shouldDisplaySuccessMessageWhenStudentIsAdded() {
        if (GraphicsEnvironment.isHeadless()) return;
        
        Student student = new Student("Ahmed", "123");
        view.studentAdded(student);
        window.label("errorLabel").requireText("Student added: Ahmed");
    }
    @Test
    public void shouldDisplayUpdateMessageWhenStudentIsUpdated() {
        if (GraphicsEnvironment.isHeadless()) return;
        
        Student student = new Student("Umer", "456");
        view.studentUpdated(student);
        window.label("errorLabel").requireText("Student updated: Umer");
    }
    @Test
    public void shouldDisplayDeleteMessageWhenStudentIsDeleted() {
        if (GraphicsEnvironment.isHeadless()) return;
        
        Student student = new Student("Sarim", "789");
        view.studentDeleted(student);
        window.label("errorLabel").requireText("Student deleted: Sarim");
    }
    @Test
    public void shouldDisplayErrorMessageWhenShowStudentErrorIsCalled() {
        if (GraphicsEnvironment.isHeadless()) return;
        
        Student student = new Student("sadii", "999");
        view.showStudentError("Test error", student);
        window.label("errorLabel").requireText("Error: Test error"); 
    }
    @Test
    public void shouldUpdateAttendanceErrorLabelWhenAttendanceIsMarked() {
        if (GraphicsEnvironment.isHeadless()) return;
        
        window.tabbedPane().selectTab("Attendance");
        window.robot().waitForIdle();
        AttendanceRecord record = new AttendanceRecord("123", new Date(), true);
        view.attendanceMarked(record);
        window.label("attendanceErrorLabel").requireText("Attendance marked for student ID: 123");
    }
    @Test
    public void shouldUpdateSummaryLabelWhenAttendancePercentageIsShown() {
        if (GraphicsEnvironment.isHeadless()) return;
        
        window.tabbedPane().selectTab("Attendance");
        window.robot().waitForIdle();
        view.showAttendancePercentage(85.5);
        window.label("summaryLabel").requireText("Overall Attendance: 85.5%");
    }
    //now it is UI integration test and integration verification
    @Test
    public void shouldAllowUserInteractionWithStudentForm() {
        if (GraphicsEnvironment.isHeadless()) return;
        window.tabbedPane().selectTab("Students");
        window.textBox("studentnameTextBox").enterText("Test");
        window.textBox("rollnumberTxtBox").enterText("123");
        window.button("addButton").click();
    }
    // now multithreading but
    @Test 
    public void shouldUpdateUIOnEventDispatchThread() {
        if (GraphicsEnvironment.isHeadless()) return;
        Student student = new Student("EDT", "999");
        SwingUtilities.invokeLater(() -> {
            view.studentAdded(student);
        });
        window.robot().waitForIdle();
        window.label("errorLabel").requireText("Student added: EDT");
    }
}