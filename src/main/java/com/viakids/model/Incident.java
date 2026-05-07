package com.viakids.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "incidents")
public class Incident {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "fecha", nullable = false)
    private String fecha;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IncidentType tipo;
    
    @Column(name = "descripcion", nullable = false)
    private String descripcion;
    
    @Column(name = "bus", nullable = false)
    private String bus;
    
    @Column(name = "resuelto")
    private Boolean resuelto = false;
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    
    public IncidentType getTipo() { return tipo; }
    public void setTipo(IncidentType tipo) { this.tipo = tipo; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public String getBus() { return bus; }
    public void setBus(String bus) { this.bus = bus; }
    
    public Boolean getResuelto() { return resuelto; }
    public void setResuelto(Boolean resuelto) { this.resuelto = resuelto; }
    
    public enum IncidentType {
        MECANICO,
        TRAFICO
    }
}
