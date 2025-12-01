package attendance.tracker.model;

public class Student {
    private String id;
    private String name;
    private String rollNumber;
    
    public Student(String name, String rollNumber) {
        this.name = name;
        this.rollNumber = rollNumber;
        
        this.id = "STU001"; 							// Simple ID for now
    }
    
    public String getId() {
        return id;
    }
}