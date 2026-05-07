package com.viakids.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "fecha")
    private String fecha;
    
    @Enumerated(EnumType.STRING)
    private NotificationType tipo;
    
    @Column(name = "mensaje", nullable = false)
    private String mensaje;
    
    @Column(name = "ruta")
    private String ruta;
    
    @Column(name = "leido")
    private Boolean leido = false;
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    
    public NotificationType getTipo() { return tipo; }
    public void setTipo(NotificationType tipo) { this.tipo = tipo; }
    
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    
    public String getRuta() { return ruta; }
    public void setRuta(String ruta) { this.ruta = ruta; }
    
    public Boolean getLeido() { return leido; }
    public void setLeido(Boolean leido) { this.leido = leido; }
    
    public enum NotificationType {
        ALERTA,
        INFO
    }
}
