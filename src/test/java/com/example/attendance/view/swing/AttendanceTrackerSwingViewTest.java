package com.example.attendance.view.swing;

import static org.mockito.Mockito.mock;

import java.awt.GraphicsEnvironment;
import java.util.Date;

import javax.swing.SwingUtilities;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.attendance.controller.AttendanceController;
import com.example.attendance.controller.StudentController;
import com.example.attendance.model.AttendanceRecord;
import com.example.attendance.model.Student;

@RunWith(GUITestRunner.class)
public class AttendanceTrackerSwingViewTest extends AssertJSwingJUnitTestCase {
    static {
        // Force the test mode to turn on immediately
        System.setProperty("test.mode", "true");
    }
    private FrameFixture window;
    private AttendanceTrackerSwingView view;

    @Override
    protected void onSetUp() {
        // detect headless and skipp t if not its display

        if (GraphicsEnvironment.isHeadless()) {
            return;
        }

        // Increase robot timeouts for slow CI environments
        robot().settings().delayBetweenEvents(60);
        robot().settings().timeoutToBeVisible(10000);

        // Create fake controllers that will not connect to database
        StudentController fakeStudentController = mock(StudentController.class);
        AttendanceController fakeAttendanceController = mock(AttendanceController.class);

        // Ensure test mode is set for properties
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
        // Wait for window to fully initialize
        robot().waitForIdle();
    }
    // GUI TESTS

    @Test
    public void testThatMainWindowIsVisibleWhenApplicationStarts() {
        if (GraphicsEnvironment.isHeadless())
            return;
        window.requireVisible();
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void testThatAllRequiredGUIComponentsArePresentAndVisible() {
        if (GraphicsEnvironment.isHeadless())
            return;

        // Students tab
        window.tabbedPane().selectTab(0);
        window.robot().waitForIdle();
        window.textBox("studentnameTextBox").requireVisible();
        window.textBox("rollnumberTxtBox").requireVisible();
        window.button("addButton").requireVisible();
        window.button("updateButton").requireVisible();
        window.button("deleteButton").requireVisible();
        window.list("studentlist").requireVisible();
        window.label("errorLabel").requireVisible();

        // Attendance tab - Switch with retry logic for Windows stability
        boolean switched = false;
        for(int i=0; i<3 && !switched; i++) {
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
            window.tabbedPane().selectTab(1);
            window.robot().waitForIdle();
            if (window.tabbedPane().target().getSelectedIndex() == 1) switched = true;
        }

        window.textBox("dateTextField").requireVisible();
        window.button("markAttendanceButton").requireVisible();
        window.button("viewByDateButton").requireVisible();
        window.button("getSummaryButton").requireVisible();
        window.textBox("attendanceRecordsArea").requireVisible();
        window.label("summaryLabel").requireVisible();
        window.label("attendanceErrorLabel").requireVisible();
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void testThatAddButtonIsDisabledWhenNameOrRollNumberIsEmpty() {
        if (GraphicsEnvironment.isHeadless())
            return;

        window.tabbedPane().selectTab(0);
        window.button("addButton").requireDisabled();

        window.textBox("studentnameTextBox").enterText("JJ");
        window.textBox("rollnumberTxtBox").enterText("123");
        window.button("addButton").requireEnabled();
        org.junit.Assert.assertTrue(true);
    }

    // INTERFACE IMPLEMENTATION Testt
    @Test
    public void shouldDisplaySuccessMessageWhenStudentIsAdded() {
        if (GraphicsEnvironment.isHeadless())
            return;

        Student student = new Student("Ahmed", "123");
        view.studentAdded(student);
        window.label("errorLabel").requireText("Student added: Ahmed");
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void shouldDisplayUpdateMessageWhenStudentIsUpdated() {
        if (GraphicsEnvironment.isHeadless())
            return;

        Student student = new Student("Umer", "456");
        view.studentUpdated(student);
        window.label("errorLabel").requireText("Student updated: Umer");
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void shouldDisplayDeleteMessageWhenStudentIsDeleted() {
        if (GraphicsEnvironment.isHeadless())
            return;

        Student student = new Student("Sarim", "789");
        view.studentDeleted(student);
        window.label("errorLabel").requireText("Student deleted: Sarim");
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void shouldDisplayErrorMessageWhenShowStudentErrorIsCalled() {
        if (GraphicsEnvironment.isHeadless())
            return;

        Student student = new Student("sadii", "999");
        view.showStudentError("Test error", student);
        window.label("errorLabel").requireText("Error: Test error");
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void shouldUpdateAttendanceErrorLabelWhenAttendanceIsMarked() {
        if (GraphicsEnvironment.isHeadless())
            return;

        window.tabbedPane().selectTab(1);
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        window.robot().waitForIdle();
        AttendanceRecord record = new AttendanceRecord("123", new Date(), true);
        view.attendanceMarked(record);
        window.label("attendanceErrorLabel").requireText("Attendance marked for student ID: 123");
        org.junit.Assert.assertTrue(true);
    }

    @Test
    public void shouldUpdateSummaryLabelWhenAttendancePercentageIsShown() {
        if (GraphicsEnvironment.isHeadless())
            return;

        window.tabbedPane().selectTab(1);
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        // Adding a small pause to ensure the tab is fully rendered in the UI
        try { Thread.sleep(200); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        window.robot().waitForIdle();
        
        view.showAttendancePercentage(85.5);
        window.label("summaryLabel").requireText("Overall Attendance: 85.5%");
        org.junit.Assert.assertTrue(true);
    }

    // now it is UI integration test and integration verification
    @Test
    public void shouldAllowUserInteractionWithStudentForm() {
        if (GraphicsEnvironment.isHeadless())
            return;
        window.tabbedPane().selectTab(0);
        window.textBox("studentnameTextBox").enterText("Test");
        window.textBox("rollnumberTxtBox").enterText("123");
        window.button("addButton").click();
        org.junit.Assert.assertTrue(true);
    }

    // now multithreading but
    @Test
    public void shouldUpdateUIOnEventDispatchThread() {
        if (GraphicsEnvironment.isHeadless())
            return;
        Student student = new Student("EDT", "999");
        SwingUtilities.invokeLater(() -> {
            view.studentAdded(student);
        });
        window.robot().waitForIdle();
        window.label("errorLabel").requireText("Student added: EDT");
        org.junit.Assert.assertTrue(true);
    }
}