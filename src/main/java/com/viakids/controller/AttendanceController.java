package com.viakids.controller;

import com.viakids.model.AttendanceRecord;
import com.viakids.model.dto.AttendanceScanResponse;
import com.viakids.service.AttendanceService;
import com.viakids.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {
    
    private final AttendanceService attendanceService;
    private final StudentService studentService;
    
    public AttendanceController(AttendanceService attendanceService, StudentService studentService) {
        this.attendanceService = attendanceService;
        this.studentService = studentService;
    }
    
    @PostMapping("/scan")
    public ResponseEntity<AttendanceScanResponse> scanQR(
            @RequestParam String qrData,
            @RequestParam AttendanceRecord.AttendanceAction action,
            @RequestParam AttendanceRecord.TripType tripType) {
        
        AttendanceRecord record = attendanceService.recordAttendance(qrData, action, tripType);
        
        AttendanceScanResponse response = new AttendanceScanResponse();
        response.setSuccess(true);
        response.setData(record);
        
        AttendanceScanResponse.StudentStatusResponse status = new AttendanceScanResponse.StudentStatusResponse();
        status.setLastAction(action.name().toLowerCase());
        status.setLastTime(record.getTimestamp().format(DateTimeFormatter.ofPattern("HH:mm")));
        status.setRecord(record);
        
        try {
            com.viakids.model.Student student = studentService.getStudentByRut(qrData);
            status.setStatus(mapStatusToFrontend(student.getEstado().name()));
        } catch (Exception e) {
            status.setStatus("Desconocido");
        }
        
        response.setStudentStatus(status);
        
        return ResponseEntity.ok(response);
    }
    
    private String mapStatusToFrontend(String status) {
        switch (status) {
            case "EN_ESPERA": return "En espera";
            case "EN_EL_BUS": return "En el bus";
            case "ENTREGADO": return "Entregado";
            case "AUSENTE": return "Ausente";
            default: return status;
        }
    }
    
    @GetMapping
    public ResponseEntity<List<AttendanceRecord>> getAttendance(
            @RequestParam(required = false) String fecha,
            @RequestParam(required = false) String busId,
            @RequestParam(required = false) String studentId) {
        LocalDateTime start = null;
        LocalDateTime end = null;
        if (fecha != null) {
            start = LocalDateTime.parse(fecha + "T00:00:00");
            end = LocalDateTime.parse(fecha + "T23:59:59");
        }
        List<AttendanceRecord> records = attendanceService.getAttendanceRecords(start, end, busId, studentId);
        return ResponseEntity.ok(records);
    }
    
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<AttendanceRecord>> getStudentAttendance(@PathVariable String studentId) {
        return ResponseEntity.ok(attendanceService.getStudentAttendance(studentId));
    }
}
