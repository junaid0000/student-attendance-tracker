package com.example.attendance.repository.mongo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.bson.Document;

import com.example.attendance.model.AttendanceRecord;
import com.example.attendance.repository.AttendanceRepository;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class AttendanceMongoRepository implements AttendanceRepository {

    private static final String RECORD_ID = "recordId";
    private static final String DATE = "date";
    private static final String PRESENT = "present";
    private static final String STUDENT_ID = "studentId";

    private MongoDatabase database;
    private MongoCollection<Document> attendanceCollection;

    public AttendanceMongoRepository(MongoClient client, String databaseName, String collectionName) {
        this.database = client.getDatabase(databaseName);
        this.attendanceCollection = database.getCollection(collectionName);
    }

    @Override
    public AttendanceRecord markAttendance(AttendanceRecord attendanceRecord) {
        Document doc = new Document()
            .append(RECORD_ID, attendanceRecord.getRecordId())
            .append(DATE, attendanceRecord.getDate())
            .append(PRESENT, attendanceRecord.isPresent())
            .append(STUDENT_ID, attendanceRecord.getStudentId());

        attendanceCollection.insertOne(doc);
        return attendanceRecord;
    }

    @Override
    public List<AttendanceRecord> findByDate(Date date) {
        List<AttendanceRecord> results = new ArrayList<>();

        // Calculate start and end of the given day for range query
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date startOfDay = cal.getTime();

        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        Date endOfDay = cal.getTime();

        for (Document doc : attendanceCollection.find(
                Filters.and(Filters.gte(DATE, startOfDay), Filters.lte(DATE, endOfDay)))) {
            results.add(new AttendanceRecord(
                doc.getString(RECORD_ID),
                doc.getDate(DATE),
                doc.getBoolean(PRESENT),
                doc.getString(STUDENT_ID)
            ));
        }

        return results;
    }

    @Override
    public List<AttendanceRecord> findByStudentId(String studentId) {
        List<AttendanceRecord> results = new ArrayList<>();

        for (Document doc : attendanceCollection.find(Filters.eq(STUDENT_ID, studentId))) {
            results.add(new AttendanceRecord(
                doc.getString(RECORD_ID),
                doc.getDate(DATE),
                doc.getBoolean(PRESENT),
                doc.getString(STUDENT_ID)
            ));
        }

        return results;
    }

    public int getAttendanceSummary(String studentId) {
        int presentCount = 0;

        for (Document doc : attendanceCollection.find(Filters.eq(STUDENT_ID, studentId))) {
            if (doc.getBoolean(PRESENT)) {
                presentCount++;
            }
        }

        return presentCount;
    }
}