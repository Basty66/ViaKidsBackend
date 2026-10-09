package com.viakids.backend.incident;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class IncidentService {

    private final IncidentRepository incidentRepository;

    public IncidentService(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    public List<Incident> getAll() {
        return incidentRepository.findAllByOrderByFechaDesc();
    }

    public Incident create(Incident incident) {
        incident.setId(null);
        if (incident.getFecha() == null) {
            incident.setFecha(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        }
        if (incident.getResuelto() == null) incident.setResuelto(false);
        return incidentRepository.save(incident);
    }
}
