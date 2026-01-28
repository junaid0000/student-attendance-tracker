package com.example.attendance;

import static org.assertj.swing.launcher.ApplicationLauncher.application;

import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GUITestRunner.class)
public class AttendanceTrackerE2ETest extends AssertJSwingJUnitTestCase {

    @Test
    public void testApplicationStartsSuccessfully() {
        application("com.example.attendance.app.AttendanceTrackerApp").start();
        throw new AssertionError("Application started but window not captured ");
    }
}