package com.viakids.backend.notification;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public List<Notification> getAll() {
        return notificationService.getAll();
    }

    @PostMapping
    public Notification create(@RequestBody Notification notification) {
        return notificationService.create(notification);
    }

    @PutMapping("/{id}/read")
    public Notification markAsRead(@PathVariable UUID id) {
        return notificationService.markAsRead(id);
    }

    @GetMapping("/presets")
    public List<PresetMessage> getPresets() {
        return notificationService.getPresets();
    }

    @PostMapping("/preset")
    public Notification sendPreset(@RequestBody PresetNotificationRequest request) {
        return notificationService.createPreset(request);
    }
}
