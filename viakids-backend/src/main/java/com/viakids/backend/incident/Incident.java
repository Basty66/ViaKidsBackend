package com.viakids.backend.incident;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "incidents")
@Data
@NoArgsConstructor
public class Incident {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String fecha;

    @Enumerated(EnumType.STRING)
    private IncidentType tipo = IncidentType.MECANICO;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    private String busPatente;

    private Boolean resuelto = false;

    public enum IncidentType {
        MECANICO, TRAFICO, CLIMA, ESTUDIANTE, OTRO
    }
}
