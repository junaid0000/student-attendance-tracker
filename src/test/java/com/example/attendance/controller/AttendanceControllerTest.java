import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.attendance.model.AttendanceRecord;
import com.example.attendance.model.Student;
import com.example.attendance.repository.AttendanceRepository;
import com.example.attendance.repository.StudentRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AttendanceControllerTest {

    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private AttendanceController attendanceController;

    private AutoCloseable closeable;

    @Before
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @After
    public void tearDown() throws Exception {
        closeable.close();
    }

public class AttendanceControllerTest {
	
	//Test for Practicing Mocking 
	
	@Test
	public void testMarkAttendancePresent() {
	    // Arrange
	    Student student = new Student("Junaid", "7131056");
	    when(studentRepository.findByRollNumber("7131056")).thenReturn(Optional.of(student));

	    AttendanceRecord record = new AttendanceRecord(student.getStudentId(), new Date(), true);
	    when(attendanceRepository.save(any(AttendanceRecord.class))).thenReturn(record);

	    // Act
	    AttendanceRecord result = attendanceController.markAttendance("7131056", new Date(), true);

	    // Assert
	    assertNotNull(result);
	    assertTrue(result.isPresent());
	    verify(attendanceRepository).save(any(AttendanceRecord.class));
	}

	@Test
	public void testMarkAttendanceAbsent() {
	    // Arrange
	    Student student = new Student("Junaid", "7131056");
	    when(studentRepository.findByRollNumber("7131056")).thenReturn(Optional.of(student));

	    AttendanceRecord record = new AttendanceRecord(student.getStudentId(), new Date(), false);
	    when(attendanceRepository.save(any(AttendanceRecord.class))).thenReturn(record);

	    // Act
	    AttendanceRecord result = attendanceController.markAttendance("7131056", new Date(), false);

	    // Assert
	    assertNotNull(result);
	    assertFalse(result.isPresent());
	    verify(attendanceRepository).save(any(AttendanceRecord.class));
	}

	@Test
	public void testGetAttendanceByDate() {
	    // Arrange
	    List<AttendanceRecord> mockRecords = Arrays.asList(
	        new AttendanceRecord("STU001", new Date(), true),
	        new AttendanceRecord("STU002", new Date(), false)
	    );
	    when(attendanceRepository.findByDate(any(Date.class))).thenReturn(mockRecords);

	    // Act
	    List<AttendanceRecord> result = attendanceController.getAttendanceByDate(new Date());

	    // Assert
	    assertNotNull(result);
	    assertEquals(2, result.size());
	    verify(attendanceRepository).findByDate(any(Date.class));
	}

	@Test
	public void testGetAttendanceByStudent() {
	    // Arrange
	    Student student = new Student("Junaid", "7131056");
	    when(studentRepository.findByRollNumber("7131056")).thenReturn(Optional.of(student));

	    List<AttendanceRecord> mockRecords = Arrays.asList(
	        new AttendanceRecord(student.getStudentId(), new Date(), true),
	        new AttendanceRecord(student.getStudentId(), new Date(), false)
	    );
	    when(attendanceRepository.findByStudentId(student.getStudentId())).thenReturn(mockRecords);

	    // Act
	    List<AttendanceRecord> result = attendanceController.getAttendanceByStudent("7131056");

	    // Assert
	    assertNotNull(result);
	    assertEquals(2, result.size());
	    verify(studentRepository).findByRollNumber("7131056");
	    verify(attendanceRepository).findByStudentId(student.getStudentId());
	}

	@Test
	public void testGetAttendancePercentage() {
	    // Arrange
	    Student student = new Student("Junaid", "7131056");
	    when(studentRepository.findByRollNumber("7131056")).thenReturn(Optional.of(student));

	    List<AttendanceRecord> mockRecords = Arrays.asList(
	        new AttendanceRecord(student.getStudentId(), new Date(), true),   // Present
	        new AttendanceRecord(student.getStudentId(), new Date(), true),   // Present
	        new AttendanceRecord(student.getStudentId(), new Date(), false)   // Absent
	    );
	    when(attendanceRepository.findByStudentId(student.getStudentId())).thenReturn(mockRecords);

	    // Act
	    double result = attendanceController.getAttendancePercentage("7131056");

	    // Assert
	    assertEquals(66.66, result, 0.01);
	    verify(studentRepository).findByRollNumber("7131056");
	    verify(attendanceRepository).findByStudentId(student.getStudentId());
	}

}