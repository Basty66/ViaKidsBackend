package com.viakids.controller;

import com.viakids.model.AttendanceRecord;
import com.viakids.model.dto.AttendanceReportDTO;
import com.viakids.service.AttendanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reports")
public class ReportsController {
    
    private final AttendanceService attendanceService;
    
    public ReportsController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }
    
    @GetMapping("/attendance")
    public ResponseEntity<List<AttendanceReportDTO>> getAttendanceReport(
            @RequestParam(required = false) String fecha,
            @RequestParam(required = false) String busId,
            @RequestParam(required = false) String ruta) {
        
        LocalDateTime start = null;
        LocalDateTime end = null;
        if (fecha != null) {
            start = LocalDateTime.parse(fecha + "T00:00:00");
            end = LocalDateTime.parse(fecha + "T23:59:59");
        }
        
        List<AttendanceRecord> records = attendanceService.getAttendanceRecords(start, end, busId, null);
        
        List<AttendanceReportDTO> report = records.stream()
            .filter(r -> ruta == null || ruta.equals(r.getRoute()))
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(report);
    }
    
    private AttendanceReportDTO convertToDTO(AttendanceRecord record) {
        AttendanceReportDTO dto = new AttendanceReportDTO();
        dto.setId(Long.valueOf(record.getId()));
        dto.setFecha(record.getTimestamp().toLocalDate().toString());
        dto.setHora(record.getTimestamp().toLocalTime().toString());
        dto.setEstudiante(record.getStudentName());
        dto.setRuta(record.getRoute());
        dto.setBus(record.getBusPatente());
        dto.setEstado(mapActionToEstado(record.getAction()));
        return dto;
    }
    
    private String mapActionToEstado(AttendanceRecord.AttendanceAction action) {
        switch (action) {
            case BOARDED: return "Abordó";
            case DISEMBARKED: return "Entregado";
            case ABSENT: return "Ausente";
            default: return "Desconocido";
        }
    }
}
