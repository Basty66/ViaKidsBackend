package com.viakids.repository;

import com.viakids.model.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, String> {
    List<AttendanceRecord> findByStudentId(String studentId);
    List<AttendanceRecord> findByBusId(String busId);
    List<AttendanceRecord> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
}
