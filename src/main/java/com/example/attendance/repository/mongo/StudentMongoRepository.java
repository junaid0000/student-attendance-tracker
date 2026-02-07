package com.example.attendance.repository.mongo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.Document;

import com.example.attendance.model.Student;
import com.example.attendance.repository.StudentRepository;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;

public class StudentMongoRepository implements StudentRepository {

    private MongoClient client;
    private MongoDatabase database;
    private MongoCollection<Document> studentCollection;

    public StudentMongoRepository(MongoClient client, String databaseName, String collectionName) {
        this.client = client;
        this.database = client.getDatabase(databaseName);
        this.studentCollection = database.getCollection(collectionName);
        studentCollection.createIndex(Indexes.ascending("rollNumber"), new IndexOptions().unique(true));
    }
    @Override
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
    @Override
    public void update(Student student) {
        Document doc = new Document()
            .append("studentId", student.getStudentId())
            .append("name", student.getName())
            .append("rollNumber", student.getRollNumber());

        studentCollection.replaceOne(
            Filters.eq("studentId", student.getStudentId()),
            doc
        );
    }
    public void delete(String studentId) {
        studentCollection.deleteOne(
            Filters.eq("studentId", studentId)
        );
    }

    @Override
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

    @Override
	public void delete(Student student) {
        delete(student.getStudentId());
    	}
    @Override
    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();
        for (Document doc : studentCollection.find()) {
            Student student = new Student(
                doc.getString("studentId"),
                doc.getString("name"),
                doc.getString("rollNumber")
            );
            students.add(student);
        }
        return students;
    }

}