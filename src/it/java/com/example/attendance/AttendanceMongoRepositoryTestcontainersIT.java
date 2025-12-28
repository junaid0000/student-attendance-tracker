package com.example.attendance;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.GenericContainer;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.example.attendance.model.AttendanceRecord;
import com.example.attendance.repository.mongo.AttendanceMongoRepository;
import java.util.Date;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class AttendanceMongoRepositoryTestcontainersIT {
    
    @SuppressWarnings("rawtypes")
    @ClassRule
    public static final GenericContainer mongo = 
        new GenericContainer("mongo:4.4.3").withExposedPorts(27017);
    
    private MongoClient client;
    private AttendanceMongoRepository repository;
    
    @Before
    public void setup() {
        client = new MongoClient(
            new ServerAddress(mongo.getContainerIpAddress(), mongo.getMappedPort(27017))
        );
        repository = new AttendanceMongoRepository(client, "attendance_db", "attendance_records");
    }
    
    @After
    public void tearDown() {
        client.close();
    }
    
    //  Mark attendance TDD
    @Test
    public void testMarkAttendance() {
        Date today = new Date();
        AttendanceRecord record = new AttendanceRecord("R1", today, true, "S1");
        repository.markAttendance(record);
        
        List<AttendanceRecord> found = repository.findByDate(today);
        assertThat(found).hasSize(1);
        assertThat(found.get(0).isPresent()).isTrue();
    }
    // View attendance by date TDD
    @Test
    public void testFindAttendanceByDate() {
        Date today = new Date();
        AttendanceRecord record1 = new AttendanceRecord("R1", today, true, "S1");
        AttendanceRecord record2 = new AttendanceRecord("R2", today, false, "S2");
        repository.markAttendance(record1);
        repository.markAttendance(record2);
        
        List<AttendanceRecord> found = repository.findByDate(today);
        assertThat(found).hasSize(2);
    }
    // View attendance by student follow TDD
    @Test
    public void testFindAttendanceByStudent() {
        Date today = new Date();
        AttendanceRecord record1 = new AttendanceRecord("R1", today, true, "S1");
        AttendanceRecord record2 = new AttendanceRecord("R2", today, true, "S1");
        repository.markAttendance(record1);
        repository.markAttendance(record2);
        
        List<AttendanceRecord> found = repository.findByStudent("S001");
        assertThat(found).hasSize(2);
    }
    
    
}