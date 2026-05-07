package com.viakids.controller;

import com.viakids.model.Incident;
import com.viakids.service.IncidentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incidents")
public class IncidentController {
    
    private final IncidentService incidentService;
    
    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }
    
    @GetMapping
    public ResponseEntity<List<Incident>> getAllIncidents() {
        return ResponseEntity.ok(incidentService.getAllIncidents());
    }
    
    @PostMapping
    public ResponseEntity<Incident> createIncident(@RequestBody Incident incident) {
        return ResponseEntity.ok(incidentService.createIncident(incident));
    }
    
    @PutMapping("/{id}/resolve")
    public ResponseEntity<Incident> resolveIncident(@PathVariable Long id) {
        return ResponseEntity.ok(incidentService.resolveIncident(id));
    }
}
