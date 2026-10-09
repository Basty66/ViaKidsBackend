package com.viakids.backend.attendance;

import com.viakids.backend.student.Student;
import com.viakids.backend.student.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;

    public AttendanceService(AttendanceRepository attendanceRepository,
                             StudentRepository studentRepository) {
        this.attendanceRepository = attendanceRepository;
        this.studentRepository = studentRepository;
    }

    public List<AttendanceRecord> getAll(Map<String, String> filters) {
        List<AttendanceRecord> records;

        if (filters.containsKey("fecha")) {
            LocalDate date = LocalDate.parse(filters.get("fecha"));
            Instant start = date.atStartOfDay(ZoneId.systemDefault()).toInstant();
            Instant end = date.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant();
            records = attendanceRepository.findByTimestampBetween(start, end);
        } else if (filters.containsKey("busId")) {
            records = attendanceRepository.findByBusId(UUID.fromString(filters.get("busId")));
        } else {
            records = attendanceRepository.findAll();
        }

        return records;
    }

    public AttendanceRecord scan(Map<String, Object> request) {
        AttendanceRecord record = new AttendanceRecord();

        if (request.containsKey("qrData")) {
            @SuppressWarnings("unchecked")
            Map<String, Object> qrData = (Map<String, Object>) request.get("qrData");
            String studentIdStr = (String) qrData.get("id");
            if (studentIdStr != null) {
                record.setStudentId(UUID.fromString(studentIdStr));
            }
            record.setStudentName((String) qrData.get("nombre"));
        }

        if (request.containsKey("studentId")) {
            record.setStudentId(UUID.fromString((String) request.get("studentId")));
        }

        String action = (String) request.get("action");
        record.setAction(AttendanceRecord.AttendanceAction.valueOf(action.toUpperCase()));

        record.setTimestamp(Instant.now());

        // Update student status
        if (record.getStudentId() != null) {
            studentRepository.findById(record.getStudentId()).ifPresent(student -> {
                switch (record.getAction()) {
                    case BOARDED -> student.setEstado(Student.StudentStatus.EN_BUS);
                    case DISEMBARKED -> student.setEstado(Student.StudentStatus.ENTREGADO);
                    case ABSENT -> student.setEstado(Student.StudentStatus.AUSENTE);
                }
                studentRepository.save(student);
            });
        }

        return attendanceRepository.save(record);
    }

    public List<AttendanceRecord> getByStudent(UUID studentId) {
        return attendanceRepository.findByStudentId(studentId);
    }

    public Map<String, Object> getTodaySummary() {
        Instant start = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant end = LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant();
        List<AttendanceRecord> today = attendanceRepository.findByTimestampBetween(start, end);

        Map<String, Object> summary = new HashMap<>();
        summary.put("boarded", today.stream().filter(r -> r.getAction() == AttendanceRecord.AttendanceAction.BOARDED).count());
        summary.put("disembarked", today.stream().filter(r -> r.getAction() == AttendanceRecord.AttendanceAction.DISEMBARKED).count());
        summary.put("absent", today.stream().filter(r -> r.getAction() == AttendanceRecord.AttendanceAction.ABSENT).count());
        summary.put("total", (long) today.size());
        return summary;
    }
}
