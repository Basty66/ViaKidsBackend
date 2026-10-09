package com.viakids.backend.report;

import com.viakids.backend.attendance.AttendanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final AttendanceService attendanceService;

    public ReportController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @GetMapping("/attendance")
    public ResponseEntity<?> getAttendanceReport(@RequestParam Map<String, String> filters) {
        return ResponseEntity.ok(attendanceService.getAll(filters));
    }
}
