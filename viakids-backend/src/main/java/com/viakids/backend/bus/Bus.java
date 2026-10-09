package com.viakids.backend.bus;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "buses")
@Data
@NoArgsConstructor
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String patente;

    @Column(nullable = false)
    private String conductor;

    private Integer capacidad = 40;

    @Enumerated(EnumType.STRING)
    private BusStatus estado = BusStatus.EN_ESPERA;

    private String tiempoEstimado = "--";

    private Double lat;

    private Double lng;

    public enum BusStatus {
        EN_RUTA, EN_ESPERA
    }
}
