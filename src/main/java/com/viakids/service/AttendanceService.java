package com.viakids.service;

import com.viakids.model.AttendanceRecord;
import com.viakids.repository.AttendanceRecordRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AttendanceService {
    
    private final AttendanceRecordRepository attendanceRecordRepository;
    
    public AttendanceService(AttendanceRecordRepository attendanceRecordRepository) {
        this.attendanceRecordRepository = attendanceRecordRepository;
    }
    
    public AttendanceRecord recordAttendance(String qrData, AttendanceRecord.AttendanceAction action, AttendanceRecord.TripType tripType) {
        AttendanceRecord record = new AttendanceRecord();
        record.setStudentId(qrData);
        record.setAction(action);
        record.setTripType(tripType);
        record.setTimestamp(LocalDateTime.now());
        return attendanceRecordRepository.save(record);
    }
    
    public List<AttendanceRecord> getAttendanceRecords(LocalDateTime start, LocalDateTime end, String busId, String studentId) {
        if (start != null && end != null) {
            return attendanceRecordRepository.findByTimestampBetween(start, end);
        }
        if (busId != null) {
            return attendanceRecordRepository.findByBusId(busId);
        }
        if (studentId != null) {
            return attendanceRecordRepository.findByStudentId(studentId);
        }
        return attendanceRecordRepository.findAll();
    }
    
    public List<AttendanceRecord> getStudentAttendance(String studentId) {
        return attendanceRecordRepository.findByStudentId(studentId);
    }
}
