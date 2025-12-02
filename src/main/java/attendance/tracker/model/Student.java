package attendance.tracker.model;

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
}