package com.viakids.backend.notification;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationProducer notificationProducer;

    public NotificationService(NotificationRepository notificationRepository,
                                NotificationProducer notificationProducer) {
        this.notificationRepository = notificationRepository;
        this.notificationProducer = notificationProducer;
    }

    public List<Notification> getAll() {
        return notificationRepository.findAllByOrderByCreatedAtDesc();
    }

    public Notification create(Notification notification) {
        notification.setId(null);
        notification.setLeido(false);
        if (notification.getFecha() == null) {
            notification.setFecha(DateTimeFormatter.ofPattern("'Hoy,' HH:mm", new Locale("es", "CL"))
                    .withZone(ZoneId.systemDefault()).format(Instant.now()));
        }
        Notification saved = notificationRepository.save(notification);
        notificationProducer.sendNotification(saved);
        return saved;
    }

    public Notification markAsRead(UUID id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));
        notification.setLeido(true);
        return notificationRepository.save(notification);
    }

    public Notification createPreset(PresetNotificationRequest request) {
        Notification notification = new Notification();
        notification.setMensaje(request.getMensaje());
        notification.setTipo(request.getTipoEnum());
        notification.setRuta(request.getRuta());
        notification.setSenderName(request.getSenderName());
        notification.setSenderRole(request.getSenderRole());
        notification.setTargetRoles(request.getTargetRoles());
        notification.setPresetKey(request.getPresetKey());
        notification.setPresetLabel(request.getPresetLabel());
        if (notification.getFecha() == null) {
            notification.setFecha(DateTimeFormatter.ofPattern("'Hoy,' HH:mm", new Locale("es", "CL"))
                    .withZone(ZoneId.systemDefault()).format(Instant.now()));
        }
        Notification saved = notificationRepository.save(notification);
        notificationProducer.sendNotification(saved);
        return saved;
    }

    public List<PresetMessage> getPresets() {
        return PresetMessage.DEFAULT_PRESETS;
    }
}
