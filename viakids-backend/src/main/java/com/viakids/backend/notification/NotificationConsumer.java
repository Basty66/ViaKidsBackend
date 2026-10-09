package com.viakids.backend.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "app.rabbitmq.enabled", havingValue = "true")
public class NotificationConsumer {

    private static final Logger log = LoggerFactory.getLogger(NotificationConsumer.class);

    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public NotificationConsumer(NotificationRepository notificationRepository,
                                 SimpMessagingTemplate messagingTemplate) {
        this.notificationRepository = notificationRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @RabbitListener(queues = "${app.rabbitmq.queue:viakids.notifications.queue}")
    public void handleNotification(Notification notification) {
        try {
            Notification saved = notificationRepository.save(notification);
            messagingTemplate.convertAndSend("/queue/notifications", saved);
            log.info("Notificación procesada y broadcast: {}", saved.getId());
        } catch (Exception e) {
            log.error("Error procesando notificación de RabbitMQ: {}", e.getMessage());
        }
    }
}
