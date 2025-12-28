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
    
    // Test 1: Mark attendance
    @Test
    public void testMarkAttendance() {
        Date today = new Date();
        AttendanceRecord record = new AttendanceRecord("R001", today, true, "S001");
        repository.markAttendance(record);
        
        List<AttendanceRecord> found = repository.findByDate(today);
        assertThat(found).hasSize(1);
        assertThat(found.get(0).isPresent()).isTrue();
    }
    
    
}