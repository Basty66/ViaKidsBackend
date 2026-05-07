package com.viakids.service;

import com.viakids.model.Incident;
import com.viakids.repository.IncidentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncidentService {
    
    private final IncidentRepository incidentRepository;
    
    public IncidentService(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }
    
    public List<Incident> getAllIncidents() {
        return incidentRepository.findAll();
    }
    
    public Incident createIncident(Incident incident) {
        return incidentRepository.save(incident);
    }
    
    public Incident resolveIncident(Long id) {
        Incident incident = incidentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Incidente no encontrado"));
        incident.setResuelto(true);
        return incidentRepository.save(incident);
    }
}
