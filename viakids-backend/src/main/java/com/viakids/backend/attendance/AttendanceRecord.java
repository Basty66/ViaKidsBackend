package com.viakids.backend.attendance;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "attendance")
@Data
@NoArgsConstructor
public class AttendanceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "student_id", nullable = false)
    private UUID studentId;

    private String studentName;

    @Column(name = "bus_id")
    private UUID busId;

    private String busPatente;

    private String route;

    @Column(nullable = false)
    private Instant timestamp = Instant.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AttendanceAction action;

    @Enumerated(EnumType.STRING)
    private TripType tripType = TripType.MORNING;

    public enum AttendanceAction {
        BOARDED, DISEMBARKED, ABSENT
    }

    public enum TripType {
        MORNING, AFTERNOON
    }
}
