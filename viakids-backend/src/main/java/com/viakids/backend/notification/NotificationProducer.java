package com.viakids.backend.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducer {

    private static final Logger log = LoggerFactory.getLogger(NotificationProducer.class);

    private final RabbitTemplate rabbitTemplate;
    private final boolean enabled;

    public NotificationProducer(RabbitTemplate rabbitTemplate,
                                 @Value("${app.rabbitmq.enabled:false}") boolean enabled) {
        this.rabbitTemplate = rabbitTemplate;
        this.enabled = enabled;
    }

    public void sendNotification(Notification notification) {
        if (!enabled) {
            log.debug("RabbitMQ deshabilitado, notificación solo persistida en DB");
            return;
        }
        try {
            rabbitTemplate.convertAndSend(
                    com.viakids.backend.config.RabbitMQConfig.EXCHANGE,
                    com.viakids.backend.config.RabbitMQConfig.ROUTING_KEY,
                    notification
            );
            log.info("Notificación publicada a RabbitMQ: {}", notification.getId());
        } catch (Exception e) {
            log.warn("No se pudo publicar a RabbitMQ (solo DB): {}", e.getMessage());
        }
    }
}
