package com.viakids.backend.attendance;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/scan")
    public ResponseEntity<?> scanQR(@RequestBody Map<String, Object> request) {
        try {
            AttendanceRecord record = attendanceService.scan(request);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "data", record,
                    "studentStatus", record.getAction()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "error", e.getMessage()));
        }
    }

    @GetMapping
    public List<AttendanceRecord> getAll(@RequestParam Map<String, String> filters) {
        return attendanceService.getAll(filters);
    }

    @GetMapping("/student/{studentId}")
    public List<AttendanceRecord> getByStudent(@PathVariable UUID studentId) {
        return attendanceService.getByStudent(studentId);
    }

    @GetMapping("/summary/today")
    public ResponseEntity<?> getTodaySummary() {
        return ResponseEntity.ok(attendanceService.getTodaySummary());
    }
}
