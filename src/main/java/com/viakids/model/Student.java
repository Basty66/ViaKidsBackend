package com.viakids.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "students")
public class Student {
    
    @Id
    @Column(name = "id", nullable = false)
    private String id;
    
    @NotBlank
    @Column(nullable = false)
    private String nombre;
    
    @NotBlank
    @Column(nullable = false)
    private String curso;
    
    @NotBlank
    @Column(nullable = false, unique = true)
    private String rut;
    
    @NotBlank
    @Column(nullable = false)
    private String apoderado;
    
    @NotBlank
    @Column(nullable = false)
    private String telefono;
    
    @Column(name = "bus_id")
    private String busId;
    
    @Column(name = "bus_patente")
    private String busPatente;
    
    @Column(name = "ruta")
    private String ruta;
    
    @NotBlank
    @Column(nullable = false)
    private String colegio;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StudentStatus estado = StudentStatus.EN_ESPERA;
    
    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = "S" + System.currentTimeMillis();
        }
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }
    
    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = rut; }
    
    public String getApoderado() { return apoderado; }
    public void setApoderado(String apoderado) { this.apoderado = apoderado; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public String getBusId() { return busId; }
    public void setBusId(String busId) { this.busId = busId; }
    
    public String getBusPatente() { return busPatente; }
    public void setBusPatente(String busPatente) { this.busPatente = busPatente; }
    
    public String getRuta() { return ruta; }
    public void setRuta(String ruta) { this.ruta = ruta; }
    
    public String getColegio() { return colegio; }
    public void setColegio(String colegio) { this.colegio = colegio; }
    
    public StudentStatus getEstado() { return estado; }
    public void setEstado(StudentStatus estado) { this.estado = estado; }
    
    public enum StudentStatus {
        EN_ESPERA,
        EN_EL_BUS,
        ENTREGADO,
        AUSENTE
    }
}
