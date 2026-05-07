package com.viakids.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "routes")
public class Route {
    
    @Id
    @Column(name = "id", nullable = false)
    private String id;
    
    @NotBlank
    @Column(nullable = false)
    private String nombre;
    
    @NotBlank
    @Column(nullable = false)
    private String colegio;
    
    @Column(name = "bus_id")
    private String busId;
    
    @Column(name = "horario")
    private String horario;
    
    @Column(name = "paradas")
    private Integer paradas;
    
    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = "R" + System.currentTimeMillis();
        }
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getColegio() { return colegio; }
    public void setColegio(String colegio) { this.colegio = colegio; }
    
    public String getBusId() { return busId; }
    public void setBusId(String busId) { this.busId = busId; }
    
    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }
    
    public Integer getParadas() { return paradas; }
    public void setParadas(Integer paradas) { this.paradas = paradas; }
}
