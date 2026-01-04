package com.example.attendance.view.swing;

import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GUITestRunner.class)
public class AttendanceTrackerSwingViewTest extends AssertJSwingJUnitTestCase {

    private FrameFixture window;
    private AttendanceTrackerSwingView view;

    @Override
    protected void onSetUp() {
        GuiActionRunner.execute(() -> {
            view = new AttendanceTrackerSwingView();
            return view;
        });
        window = new FrameFixture(robot(), view);
        window.show(); // this is shows the frame to test
    }

    @Test
    public void testInitialState() {
        // this is Just to check the setup works
        window.requireVisible();
    }
}