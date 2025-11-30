package attendance.tracker.model;

public class Student {
    private String studentId;
    private String name;
    private String rollNumber;
    
    public Student(String name, String rollNumber) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.studentId = "STU001";
    }
    
    public String getStudentId() {
        return studentId;
    }
}