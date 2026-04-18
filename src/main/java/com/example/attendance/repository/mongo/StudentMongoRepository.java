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

    private MongoCollection<Document> studentCollection;
    
    private static final String STUDENT_ID = "studentId";
    private static final String NAME = "name";
    private static final String ROLL_NUMBER = "rollNumber";

    public StudentMongoRepository(MongoClient client, String databaseName, String collectionName) {
        MongoDatabase database = client.getDatabase(databaseName);
        this.studentCollection = database.getCollection(collectionName);
        studentCollection.createIndex(Indexes.ascending("rollNumber"), new IndexOptions().unique(true));
    }
    @Override
    public Student save(Student student) {
        Document doc = new Document()
            .append(STUDENT_ID, student.getStudentId())
            .append(NAME, student.getName())
            .append(ROLL_NUMBER, student.getRollNumber());
        
        studentCollection.insertOne(doc);
        return student;
    }

    public Student findById(String studentId) {
        Document doc = studentCollection.find(
            Filters.eq(STUDENT_ID, studentId)
        ).first();

        if (doc == null) {
            return null;
        }

        return new Student(
            doc.getString(STUDENT_ID),
            doc.getString(NAME),
            doc.getString(ROLL_NUMBER)
        );
    }
    @Override
    public void update(Student student) {
        Document doc = new Document()
            .append(STUDENT_ID, student.getStudentId())
            .append(NAME, student.getName())
            .append(ROLL_NUMBER, student.getRollNumber());

        studentCollection.replaceOne(
            Filters.eq(STUDENT_ID, student.getStudentId()),
            doc
        );
    }
    public void delete(String studentId) {
        studentCollection.deleteOne(
            Filters.eq(STUDENT_ID, studentId)
        );
    }

    @Override
	public Optional<Student> findByRollNumber(String rollNumber) {
        Document doc = studentCollection.find(
            Filters.eq(ROLL_NUMBER, rollNumber)
        ).first();

        if (doc == null) {
            return Optional.empty();
        }

        Student student = new Student(
            doc.getString(STUDENT_ID),
            doc.getString(NAME),
            doc.getString(ROLL_NUMBER)
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
                doc.getString(STUDENT_ID),
                doc.getString(NAME),
                doc.getString(ROLL_NUMBER)
            );
            students.add(student);
        }
        return students;
    }

}