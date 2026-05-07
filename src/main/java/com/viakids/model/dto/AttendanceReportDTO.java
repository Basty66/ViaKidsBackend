package com.viakids.model.dto;

public class AttendanceReportDTO {
    private Long id;
    private String fecha;
    private String hora;
    private String estudiante;
    private String ruta;
    private String bus;
    private String estado;
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    
    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }
    
    public String getEstudiante() { return estudiante; }
    public void setEstudiante(String estudiante) { this.estudiante = estudiante; }
    
    public String getRuta() { return ruta; }
    public void setRuta(String ruta) { this.ruta = ruta; }
    
    public String getBus() { return bus; }
    public void setBus(String bus) { this.bus = bus; }
    
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
