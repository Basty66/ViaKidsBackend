package com.viakids.backend.notification;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String fecha;

    @Enumerated(EnumType.STRING)
    private NotificationType tipo = NotificationType.INFO;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensaje;

    private String ruta;

    private Boolean leido = false;

    private String senderName;

    private String senderRole;

    private String targetRoles;

    private String presetKey;

    private String presetLabel;

    @CreationTimestamp
    private Instant createdAt;

    public enum NotificationType {
        ALERTA, INFO, URGENTE
    }
}
