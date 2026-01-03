package com.example.attendance.repository.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import com.example.attendance.model.Student;
import com.example.attendance.repository.StudentRepository;
import java.util.Optional;

public class StudentMongoRepository implements StudentRepository {
    
    private MongoClient client;
    private MongoDatabase database;
    private MongoCollection<Document> studentCollection;
    
    public StudentMongoRepository(MongoClient client, String databaseName, String collectionName) {
        this.client = client;
        this.database = client.getDatabase(databaseName);
        this.studentCollection = database.getCollection(collectionName);
    }
    
    public Student save(Student student) {
        Document doc = new Document()
            .append("studentId", student.getStudentId())
            .append("name", student.getName())
            .append("rollNumber", student.getRollNumber());
        
        studentCollection.insertOne(doc);
        return student;
    }
    
    public Student findById(String studentId) {
        Document doc = studentCollection.find(
            Filters.eq("studentId", studentId)
        ).first();
        
        if (doc == null) {
            return null;
        }
        
        return new Student(
            doc.getString("studentId"),
            doc.getString("name"),
            doc.getString("rollNumber")
        );
    }
    
    public void update(Student student) {
        studentCollection.updateOne(
            Filters.eq("studentId", student.getStudentId()),
            Updates.combine(
                Updates.set("name", student.getName()),
                Updates.set("rollNumber", student.getRollNumber())
            )
        );
    }
    
    public void delete(String studentId) {
        studentCollection.deleteOne(
            Filters.eq("studentId", studentId)
        );
    }
    
    public Optional<Student> findByRollNumber(String rollNumber) {
        Document doc = studentCollection.find(
            Filters.eq("rollNumber", rollNumber)
        ).first();
        
        if (doc == null) {
            return Optional.empty();
        }
        
        Student student = new Student(
            doc.getString("studentId"),
            doc.getString("name"),
            doc.getString("rollNumber")
        );
        
        return Optional.of(student);
    }
     
    public void delete(Student student) {
        delete(student.getStudentId());
    	}
	 
}