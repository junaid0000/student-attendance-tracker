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
}