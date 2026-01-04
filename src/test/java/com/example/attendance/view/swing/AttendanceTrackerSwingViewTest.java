package com.example.attendance.view.swing;

import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.awt.GraphicsEnvironment; // ADDED LINE 1

@RunWith(GUITestRunner.class)
public class AttendanceTrackerSwingViewTest extends AssertJSwingJUnitTestCase {

    private FrameFixture window;
    private AttendanceTrackerSwingView view;

    @Override
    protected void onSetUp() {
        if (GraphicsEnvironment.isHeadless()) {
            return; // Skip test in CI (GitHub Actions)
        }
        
        GuiActionRunner.execute(() -> {
            view = new AttendanceTrackerSwingView();
            return view;
        });
        window = new FrameFixture(robot(), view);
        window.show(); // this is shows the frame to test
    }

    @Test
    public void testInitialState() {
        if (GraphicsEnvironment.isHeadless()) {
            return; // Skip test in CI (GitHub Actions)
        }
        
        // this is Just to check the setup works
        window.requireVisible();
    }
}