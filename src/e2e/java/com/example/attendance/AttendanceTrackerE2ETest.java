package com.example.attendance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.launcher.ApplicationLauncher.application;

import java.util.Arrays;
import java.util.regex.Pattern;

import javax.swing.JFrame;

import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.bson.Document;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.testcontainers.containers.MongoDBContainer;

import com.mongodb.MongoClient;
import com.mongodb.client.model.Filters;

@RunWith(GUITestRunner.class)
public class AttendanceTrackerE2ETest extends AssertJSwingJUnitTestCase {

    @ClassRule
    public static final MongoDBContainer mongo = new MongoDBContainer("mongo:4.4.3");

    private static final String DB_NAME = "test-db";
    private static final String STUDENT_COLLECTION = "students";
    private static final String ATTENDANCE_COLLECTION = "attendance";

    private MongoClient mongoClient;
    private FrameFixture window;

    // Test data
    private static final String STUDENT_1_ROLL = "7131056";
    private static final String STUDENT_1_NAME = "Junaid";

    private static final String STUDENT_2_ROLL = "7131057";
    private static final String STUDENT_2_NAME = "Awais";

    @Override
    protected void onSetUp() {
        // Clear test.mode to prevent unit test
        System.clearProperty("test.mode");

        String containerIpAddress = mongo.getContainerIpAddress();
        Integer mappedPort = mongo.getFirstMappedPort();

        mongoClient = new MongoClient(containerIpAddress, mappedPort);

        // start with empty database
        mongoClient.getDatabase(DB_NAME).drop();

        // Add test students to database
        addTestStudentToDatabase(STUDENT_1_ROLL, STUDENT_1_NAME);
        addTestStudentToDatabase(STUDENT_2_ROLL, STUDENT_2_NAME);

        // Start the REAL Swing Application
        application("com.example.attendance.app.AttendanceTrackerApp").withArgs("--mongo-host=" + containerIpAddress,
                "--mongo-port=" + mappedPort.toString(), "--db-name=" + DB_NAME,
                "--db-student-collection=" + STUDENT_COLLECTION, "--db-attendance-collection=" + ATTENDANCE_COLLECTION)
                .start();

        // Get reference to the application JFrame
        window = org.assertj.swing.finder.WindowFinder.findFrame(new GenericTypeMatcher<JFrame>(JFrame.class) {
            @Override
            protected boolean isMatching(JFrame frame) {
                return "Student Attendance Tracker".equals(frame.getTitle()) && frame.isShowing();
            }
        }).using(robot());

        // Wait for UI to load completely
        window.robot().waitForIdle();
        window.robot().waitForIdle();
    }

    @Override
    protected void onTearDown() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }

    private void addTestStudentToDatabase(String rollNumber, String name) {
        mongoClient.getDatabase(DB_NAME).getCollection(STUDENT_COLLECTION)
                .insertOne(new Document().append("rollNumber", rollNumber).append("name", name).append("studentId",
                        java.util.UUID.randomUUID().toString()));
    }

    // Verify students from database are shown on startup
    @Test
    @GUITest
    public void testOnStartAllDatabaseStudentsAreShown() throws InterruptedException {
        window.tabbedPane().selectTab("Students");

        // Wait longer for students to load
        window.robot().waitForIdle();
        Thread.sleep(1000);

        // Get list contents
        String[] listContents = window.list("studentlist").contents();
        System.out.println("List contents: " + Arrays.toString(listContents)); // Debug

        assertThat(listContents).anySatisfy(e -> assertThat(e).contains(STUDENT_1_ROLL))
                .anySatisfy(e -> assertThat(e).contains(STUDENT_2_ROLL));
    }

    // Add new student through UI and verify in database
    @Test
    @GUITest
    public void testAddNewStudentSuccess() {
        window.tabbedPane().selectTab("Students");
        window.textBox("studentnameTextBox").enterText("New Student");
        window.textBox("rollnumberTxtBox").enterText("1001");

        // Click Add button
        window.button("addButton").click();
        window.robot().waitForIdle();

        // Verify success message in UI
        assertThat(window.label("errorLabel").text()).contains("Student added");

        // Verify student saved in database
        Document studentDoc = mongoClient.getDatabase(DB_NAME).getCollection(STUDENT_COLLECTION)
                .find(Filters.eq("rollNumber", "1001")).first();

        assertThat(studentDoc).isNotNull();
        assertThat(studentDoc.getString("name")).isEqualTo("New Student");
    }

    // Test duplicate student error
    @Test
    @GUITest
    public void testAddDuplicateStudentShowsError() {
        window.tabbedPane().selectTab("Students");
        window.robot().waitForIdle();

        // Try to add student that already exists in database
        window.textBox("studentnameTextBox").enterText("Duplicate");
        window.textBox("rollnumberTxtBox").enterText(STUDENT_1_ROLL);
        window.button("addButton").click();
        window.robot().waitForIdle();

        // ADD A SMALL DELAY TO ERROR APPEAR
        try {
            Thread.sleep(1000); // Wait 1 second for error message
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Verify error message will check already exists
        assertThat(window.label("errorLabel").text()).contains("already exists");
    }

    // Delete student through UI
    @Test
    @GUITest
    public void testDeleteStudentFunctionality() {
        window.tabbedPane().selectTab("Students");
        window.robot().waitForIdle();

        // Wait for list to load
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        window.list("studentlist").selectItem(Pattern.compile(".*" + STUDENT_1_ROLL + ".*"));
        window.button("deleteButton").click();

        // Wait for deletion
        window.robot().waitForIdle();

        // Verify success message
        assertThat(window.label("errorLabel").text()).contains("Loaded");

        // Verify removed from database
        Document studentDoc = mongoClient.getDatabase(DB_NAME).getCollection(STUDENT_COLLECTION)
                .find(Filters.eq("rollNumber", STUDENT_1_ROLL)).first();

        assertThat(studentDoc).isNull();
    }

}