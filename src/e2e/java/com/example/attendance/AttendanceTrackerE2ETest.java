package com.example.attendance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.launcher.ApplicationLauncher.application;

import java.util.regex.Pattern;

import javax.swing.JFrame;

import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.core.matcher.JButtonMatcher;
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
        String containerIpAddress = mongo.getContainerIpAddress();
        Integer mappedPort = mongo.getFirstMappedPort();

        mongoClient = new MongoClient(containerIpAddress, mappedPort);

        // start with empty database
        mongoClient.getDatabase(DB_NAME).drop();

        // Add test students to database
        addTestStudentToDatabase(STUDENT_1_ROLL, STUDENT_1_NAME);
        addTestStudentToDatabase(STUDENT_2_ROLL, STUDENT_2_NAME);

        // Start the REAL Swing Application 
        application("com.example.attendance.app.AttendanceTrackerApp")
            .withArgs(
                "--mongo-host=" + containerIpAddress,
                "--mongo-port=" + mappedPort.toString(),
                "--db-name=" + DB_NAME,
                "--db-student-collection=" + STUDENT_COLLECTION,
                "--db-attendance-collection=" + ATTENDANCE_COLLECTION
            )
            .start();

        // Get reference to the application JFrame
        window = org.assertj.swing.finder.WindowFinder.findFrame(
            new GenericTypeMatcher<JFrame>(JFrame.class) {
                @Override
                protected boolean isMatching(JFrame frame) {
                    return "Student Attendance Tracker".equals(frame.getTitle()) 
                           && frame.isShowing();
                }
            }).using(robot());
    }

    @Override
    protected void onTearDown() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }

    private void addTestStudentToDatabase(String rollNumber, String name) {
        mongoClient.getDatabase(DB_NAME).getCollection(STUDENT_COLLECTION)
            .insertOne(new Document()
                .append("rollNumber", rollNumber)
                .append("name", name));
    }

    // Verify students from database are shown on startup
    @Test
    @GUITest
    public void testOnStartAllDatabaseStudentsAreShown() {
        window.tabbedPane().selectTab("Students");
       
        assertThat(window.list("studentlist").contents())
        .anySatisfy(e -> assertThat(e).contains(STUDENT_1_ROLL))
        .anySatisfy(e -> assertThat(e).contains(STUDENT_2_ROLL));
    }

    // Add new student through UI and verify in database
    @Test
    @GUITest
    public void testAddNewStudentSuccess() {
        window.tabbedPane().selectTab("Students");
        
        // Enter new student in UI
        window.textBox("studentnameTextBox").enterText("New Student");
        window.textBox("rollnumberTxtBox").enterText("1001");
        
        //  Click Add button
        window.button(JButtonMatcher.withText("Add")).click();
        
        // Verify success message in UI
        window.label("errorLabel").requireText("Student added: New Student");
        
        // Verify student saved in database
        Document studentDoc = mongoClient.getDatabase(DB_NAME)
            .getCollection(STUDENT_COLLECTION)
            .find(Filters.eq("rollNumber", "1001"))
            .first();
            
        assertThat(studentDoc).isNotNull();
        assertThat(studentDoc.getString("name")).isEqualTo("New Student");
    }

    // Test duplicate student error
    @Test
    @GUITest
    public void testAddDuplicateStudentShowsError() {
        window.tabbedPane().selectTab("Students");
        
        // Try to add student that already exists in database
        window.textBox("studentnameTextBox").enterText("Duplicate");
        window.textBox("rollnumberTxtBox").enterText(STUDENT_1_ROLL);
        window.button(JButtonMatcher.withText("Add")).click();
        
        // Verify error message
        window.label("errorLabel").requireText("Error: Student already exists");
    }

    // Delete student through UI
    @Test
    @GUITest
    public void testDeleteStudentFunctionality() {
        window.tabbedPane().selectTab("Students");
        
        // Select student from list
        window.list("studentlist").selectItem(Pattern.compile(".*" + STUDENT_1_ROLL + ".*"));
        
        // Click Delete button
        window.button(JButtonMatcher.withText("Delete")).click();
        
        // Verify success message
        window.label("errorLabel").requireText("Student deleted: " + STUDENT_1_NAME);
        
        // Verify removed from database
        Document studentDoc = mongoClient.getDatabase(DB_NAME)
            .getCollection(STUDENT_COLLECTION)
            .find(Filters.eq("rollNumber", STUDENT_1_ROLL))
            .first();
            
        assertThat(studentDoc).isNull();
    }
}