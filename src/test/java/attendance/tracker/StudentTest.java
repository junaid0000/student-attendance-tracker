package attendance.tracker;

import static org.junit.Assert.*;
import org.junit.Test;

public class StudentTest {

    @Test
    public void testCreateStudentWithValidData() {
       
        Student student = new Student("Junaid", "7131056");
        assertNotNull("Student ID should be automatically assigned", 
                     student.getStudentId());
    }
}