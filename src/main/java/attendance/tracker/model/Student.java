package attendance.tracker.model;

import java.util.Objects;

public class Student {
    private String id;
    private String name;
    private String rollNumber;
    
    public Student(String name, String rollNumber) {
        this.name = name;
        
        this.rollNumber = rollNumber;
        
        this.id = "STU001"; 							// Simple ID for
    }
    
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getRollNumber() {
        return rollNumber;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Student other = (Student) obj;
        return Objects.equals(id, other.id);
    }

    public void setId(String id) {
        this.id = id; // setter for id.
    }
    @Override
    public int hashCode() {
        if (id == null) {
            return 0;
        }
        return id.hashCode();
    }
}