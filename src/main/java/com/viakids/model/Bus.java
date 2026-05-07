package com.viakids.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "buses")
public class Bus {
    
    @Id
    @Column(name = "id", nullable = false)
    private String id;
    
    @NotBlank
    @Column(nullable = false, unique = true)
    private String patente;
    
    @NotBlank
    @Column(nullable = false)
    private String conductor;
    
    @NotNull
    @Column(nullable = false)
    private Integer capacidad;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BusStatus estado = BusStatus.EN_ESPERA;
    
    @Column(name = "tiempo_estimado")
    private String tiempoEstimado;
    
    private Double lat;
    
    private Double lng;
    
    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = "B" + System.currentTimeMillis();
        }
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getPatente() { return patente; }
    public void setPatente(String patente) { this.patente = patente; }
    
    public String getConductor() { return conductor; }
    public void setConductor(String conductor) { this.conductor = conductor; }
    
    public Integer getCapacidad() { return capacidad; }
    public void setCapacidad(Integer capacidad) { this.capacidad = capacidad; }
    
    public BusStatus getEstado() { return estado; }
    public void setEstado(BusStatus estado) { this.estado = estado; }
    
    public String getTiempoEstimado() { return tiempoEstimado; }
    public void setTiempoEstimado(String tiempoEstimado) { this.tiempoEstimado = tiempoEstimado; }
    
    public Double getLat() { return lat; }
    public void setLat(Double lat) { this.lat = lat; }
    
    public Double getLng() { return lng; }
    public void setLng(Double lng) { this.lng = lng; }
    
    public enum BusStatus {
        EN_RUTA,
        EN_ESPERA
    }
}
