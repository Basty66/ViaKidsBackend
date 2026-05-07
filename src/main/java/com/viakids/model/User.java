package com.viakids.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Column(nullable = false)
    private String nombre;
    
    @Email
    @NotBlank
    @Column(nullable = false, unique = true)
    private String email;
    
    @NotBlank
    @Column(nullable = false)
    @JsonIgnore
    private String password;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole rol;
    
    @Column(name = "telefono")
    private String telefono;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus estado = UserStatus.ACTIVO;
    
    @Column(name = "extra")
    private String extra;
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public UserRole getRol() { return rol; }
    public void setRol(UserRole rol) { this.rol = rol; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public UserStatus getEstado() { return estado; }
    public void setEstado(UserStatus estado) { this.estado = estado; }
    
    public String getExtra() { return extra; }
    public void setExtra(String extra) { this.extra = extra; }
    
    public enum UserRole {
        ADMIN,
        DRIVER,
        PARENT
    }
    
    public enum UserStatus {
        ACTIVO,
        SUSPENDIDO
    }
}
