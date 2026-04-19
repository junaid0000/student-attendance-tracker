package com.example.attendance.view.swing;

import static org.assertj.core.api.Assertions.assertThat;
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
    private static final String TB_STUDENT_NAME = "studentnameTextBox";
    private static final String TB_ROLL_NUMBER = "rollnumberTxtBox";
    private static final String BTN_ADD = "addButton";
    private static final String LBL_ERROR = "errorLabel";
    private static final String LIST_STUDENT = "studentlist";
    private static final String TB_DATE = "dateTextField";
    private static final String BTN_MARK = "markAttendanceButton";
    private static final String BTN_VIEW_BY_DATE = "viewByDateButton";
    private static final String BTN_GET_SUMMARY = "getSummaryButton";
    private static final String TA_RECORDS = "attendanceRecordsArea";
    private static final String LBL_SUMMARY = "summaryLabel";
    private static final String LBL_ATTENDANCE_ERROR = "attendanceErrorLabel";
    
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
        assertThat(view.isVisible()).isTrue();
    }

    @Test
    public void testThatAllRequiredGUIComponentsArePresentAndVisible() {
        if (GraphicsEnvironment.isHeadless())
            return;

        // Students tab
        window.tabbedPane().selectTab(0);
        window.robot().waitForIdle();
        window.textBox(TB_STUDENT_NAME).requireVisible();
        window.textBox(TB_ROLL_NUMBER).requireVisible();
        window.button(BTN_ADD).requireVisible();
        window.button("updateButton").requireVisible();
        window.button("deleteButton").requireVisible();
        window.list(LIST_STUDENT).requireVisible();
        window.label(LBL_ERROR).requireVisible();

        // Attendance tab - Use direct EDT switch for CI stability
        GuiActionRunner.execute(() -> window.tabbedPane().target().setSelectedIndex(1));
        window.robot().waitForIdle();
        org.assertj.swing.timing.Pause.pause(1000); // Allow time for components to be "showing"

        window.robot().waitForIdle();
        assertThat(window.textBox(TB_DATE).target().isVisible()).isTrue();
        assertThat(window.button(BTN_MARK).target().isVisible()).isTrue();
        assertThat(window.button(BTN_VIEW_BY_DATE).target().isVisible()).isTrue();
        assertThat(window.button(BTN_GET_SUMMARY).target().isVisible()).isTrue();
        assertThat(window.textBox(TA_RECORDS).target().isVisible()).isTrue();
        assertThat(window.label(LBL_SUMMARY).target().isVisible()).isTrue();
        assertThat(window.label(LBL_ATTENDANCE_ERROR).target().isVisible()).isTrue();
    }

    @Test
    public void testThatAddButtonIsDisabledWhenNameOrRollNumberIsEmpty() {
        if (GraphicsEnvironment.isHeadless())
            return;

        window.tabbedPane().selectTab(0);
        assertThat(window.button(BTN_ADD).target().isEnabled()).isFalse();

        window.textBox(TB_STUDENT_NAME).enterText("JJ");
        window.textBox(TB_ROLL_NUMBER).enterText("123");
        assertThat(window.button(BTN_ADD).target().isEnabled()).isTrue();
    }

    // INTERFACE IMPLEMENTATION Testt
    @Test
    public void shouldDisplaySuccessMessageWhenStudentIsAdded() {
        if (GraphicsEnvironment.isHeadless())
            return;

        Student student = new Student("Ahmed", "123");
        view.studentAdded(student);
        window.robot().waitForIdle();
        assertThat(window.label(LBL_ERROR).text()).isEqualTo("Student added: Ahmed");
    }

    @Test
    public void shouldDisplayUpdateMessageWhenStudentIsUpdated() {
        if (GraphicsEnvironment.isHeadless())
            return;

        Student student = new Student("Umer", "456");
        view.studentUpdated(student);
        window.robot().waitForIdle();
        assertThat(window.label(LBL_ERROR).text()).isEqualTo("Student updated: Umer");
    }

    @Test
    public void shouldDisplayDeleteMessageWhenStudentIsDeleted() {
        if (GraphicsEnvironment.isHeadless())
            return;

        Student student = new Student("Sarim", "789");
        view.studentDeleted(student);
        window.robot().waitForIdle();
        assertThat(window.label(LBL_ERROR).text()).isEqualTo("Student deleted: Sarim");
    }

    @Test
    public void shouldDisplayErrorMessageWhenShowStudentErrorIsCalled() {
        if (GraphicsEnvironment.isHeadless())
            return;

        Student student = new Student("sadii", "999");
        view.showStudentError("Test error", student);
        window.robot().waitForIdle();
        assertThat(window.label(LBL_ERROR).text()).isEqualTo("Error: Test error");
    }

    @Test
    public void shouldUpdateAttendanceErrorLabelWhenAttendanceIsMarked() {
        if (GraphicsEnvironment.isHeadless())
            return;

        boolean switched = false;
        for(int i=0; i<3 && !switched; i++) {
            org.assertj.swing.timing.Pause.pause(1000);
            window.tabbedPane().selectTab(1);
            window.robot().waitForIdle();
            if (window.tabbedPane().target().getSelectedIndex() == 1) switched = true;
        }
        AttendanceRecord attendanceRecord = new AttendanceRecord("123", new Date(), true);
        view.attendanceMarked(attendanceRecord);
        window.robot().waitForIdle();
        assertThat(window.label(LBL_ATTENDANCE_ERROR).text()).isEqualTo("Attendance marked for student ID: 123");
    }

    @Test
    public void shouldUpdateSummaryLabelWhenAttendancePercentageIsShown() {
        if (GraphicsEnvironment.isHeadless())
            return;

        boolean switched = false;
        for(int i=0; i<3 && !switched; i++) {
            org.assertj.swing.timing.Pause.pause(1000);
            window.tabbedPane().selectTab(1);
            window.robot().waitForIdle();
            if (window.tabbedPane().target().getSelectedIndex() == 1) switched = true;
        }
        
        view.showAttendancePercentage(85.5);
        window.robot().waitForIdle();
        assertThat(window.label(LBL_SUMMARY).text()).isEqualTo("Overall Attendance: 85.5%");
    }

    // now it is UI integration test and integration verification
    @Test
    public void shouldAllowUserInteractionWithStudentForm() {
        if (GraphicsEnvironment.isHeadless())
            return;
        window.tabbedPane().selectTab(0);
        window.textBox(TB_STUDENT_NAME).enterText("Test");
        window.textBox(TB_ROLL_NUMBER).enterText("123");
        window.button(BTN_ADD).click();
        assertThat(window.button(BTN_ADD).target().isShowing()).isTrue();
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
        assertThat(window.label(LBL_ERROR).text()).isEqualTo("Student added: EDT");
    }
}