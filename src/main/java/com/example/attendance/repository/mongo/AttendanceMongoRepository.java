package com.example.attendance.repository.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import com.example.attendance.model.AttendanceRecord;
import com.example.attendance.repository.AttendanceRepository;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class AttendanceMongoRepository implements AttendanceRepository {
    
    private MongoDatabase database;
    private MongoCollection<Document> attendanceCollection;
    
    public AttendanceMongoRepository(MongoClient client, String databaseName, String collectionName) {
        this.database = client.getDatabase(databaseName);
        this.attendanceCollection = database.getCollection(collectionName);
    }
    
    public AttendanceRecord markAttendance(AttendanceRecord record) {
        Document doc = new Document()
            .append("recordId", record.getRecordId())
            .append("date", record.getDate())
            .append("present", record.isPresent())
            .append("studentId", record.getStudentId());
        
        attendanceCollection.insertOne(doc);
        return record;
    }
    
    public List<AttendanceRecord> findByDate(Date date) {
        List<AttendanceRecord> results = new ArrayList<>();
        
        for (Document doc : attendanceCollection.find(Filters.eq("date", date))) {
            results.add(new AttendanceRecord(
                doc.getString("recordId"),
                doc.getDate("date"),
                doc.getBoolean("present"),
                doc.getString("studentId")
            ));
        }
        
        return results;
    }
    
    public List<AttendanceRecord> findByStudentId(String studentId) {
        List<AttendanceRecord> results = new ArrayList<>();
        
        for (Document doc : attendanceCollection.find(Filters.eq("studentId", studentId))) {
            results.add(new AttendanceRecord(
                doc.getString("recordId"),
                doc.getDate("date"),
                doc.getBoolean("present"),
                doc.getString("studentId")
            ));
        }
        
        return results;
    }
    
    public int getAttendanceSummary(String studentId) {
        int presentCount = 0;
        
        for (Document doc : attendanceCollection.find(Filters.eq("studentId", studentId))) {
            if (doc.getBoolean("present")) {
                presentCount++;
            }
        }
        
        return presentCount;
    }
}