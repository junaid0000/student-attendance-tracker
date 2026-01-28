package com.example.attendance;

import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.testcontainers.containers.MongoDBContainer;

@RunWith(GUITestRunner.class)
public class AttendanceTrackerE2ETest extends AssertJSwingJUnitTestCase {

    @ClassRule
    public static final MongoDBContainer mongo = new MongoDBContainer("mongo:4.4.3");

    @Test
    public void testApplicationStartsSuccessfully() {
        // so it will fail 
        throw new AssertionError("startup not implemented ");
    }
}