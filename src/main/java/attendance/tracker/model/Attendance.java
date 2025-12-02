package attendance.tracker.model;

import java.util.Date;

public class Attendance {
    private String id;
    private String studentId;
    private Date date;
    private boolean present;
    
    public Attendance(String studentId, Date date, boolean present) {
        this.studentId = studentId;
        this.date = date;
        this.present = present;
        this.id = "ATT001"; // Simple ID for now
    }
    
    public String getId() {
        return id;
    }
}