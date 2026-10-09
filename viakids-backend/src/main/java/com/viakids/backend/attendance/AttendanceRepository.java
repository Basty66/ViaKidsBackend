package com.viakids.backend.attendance;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface AttendanceRepository extends JpaRepository<AttendanceRecord, UUID> {
    List<AttendanceRecord> findByStudentId(UUID studentId);
    List<AttendanceRecord> findByBusId(UUID busId);
    List<AttendanceRecord> findByTimestampBetween(Instant start, Instant end);
    List<AttendanceRecord> findByAction(AttendanceRecord.AttendanceAction action);
}
