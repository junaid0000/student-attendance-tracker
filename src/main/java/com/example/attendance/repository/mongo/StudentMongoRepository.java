package com.example.attendance.repository.mongo;

import com.mongodb.MongoClient;
import com.example.attendance.model.Student;

public class StudentMongoRepository {
    
    public StudentMongoRepository(MongoClient client, String databaseName, String collectionName) {
        // Empty constructor
    }
    
    public Student save(Student student) {
        throw new RuntimeException("save() not implemented");
    }
    
    public Student findById(String studentId) {
        throw new RuntimeException("findById() not implemented");
    }
    
    public void update(Student student) {
        throw new RuntimeException("update() not implemented");
    }
    
    public void delete(String studentId) {
        throw new RuntimeException("delete() not implemented");
    }
}