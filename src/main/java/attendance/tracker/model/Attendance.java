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
    public String getStudentId() {
        return studentId;
    }
    public Date getDate() {
        return date;
    }
    public boolean isPresent() {
        return present;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public void setPresent(boolean present) {
        this.present = present;
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
        Attendance other = (Attendance) obj;
        if (id == null) {
            return other.id == null;
        } else {
            return id.equals(other.id);
        }
    }
    public void setId(String id) {
        this.id = id;
    }
    @Override
    public int hashCode() {
        if (id == null) {
            return 0;
        }
        return id.hashCode();
    }
}