package com.viakids.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "attendance_records")
public class AttendanceRecord {
    
    @Id
    @Column(name = "id", nullable = false)
    private String id;
    
    @Column(name = "student_id", nullable = false)
    private String studentId;
    
    @Column(name = "student_name")
    private String studentName;
    
    @Column(name = "bus_id")
    private String busId;
    
    @Column(name = "bus_patente")
    private String busPatente;
    
    @Column(name = "route")
    private String route;
    
    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AttendanceAction action;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "trip_type", nullable = false)
    private TripType tripType;
    
    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = "A" + System.currentTimeMillis();
        }
        if (timestamp == null) {
            timestamp = LocalDateTime.now();
        }
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    
    public String getBusId() { return busId; }
    public void setBusId(String busId) { this.busId = busId; }
    
    public String getBusPatente() { return busPatente; }
    public void setBusPatente(String busPatente) { this.busPatente = busPatente; }
    
    public String getRoute() { return route; }
    public void setRoute(String route) { this.route = route; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    public AttendanceAction getAction() { return action; }
    public void setAction(AttendanceAction action) { this.action = action; }
    
    public TripType getTripType() { return tripType; }
    public void setTripType(TripType tripType) { this.tripType = tripType; }
    
    public enum AttendanceAction {
        BOARDED,
        DISEMBARKED,
        ABSENT
    }
    
    public enum TripType {
        MORNING,
        AFTERNOON
    }
}
