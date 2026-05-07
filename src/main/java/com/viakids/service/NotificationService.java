package com.viakids.service;

import com.viakids.model.Notification;
import com.viakids.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    
    private final NotificationRepository notificationRepository;
    
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }
    
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }
    
    public Notification createNotification(Notification notification) {
        return notificationRepository.save(notification);
    }
    
    public Notification markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));
        notification.setLeido(true);
        return notificationRepository.save(notification);
    }
}
