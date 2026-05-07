package com.viakids.controller;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    
    private final SimpMessagingTemplate messagingTemplate;
    
    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
    
    public void sendNotification(Object payload) {
        messagingTemplate.convertAndSend("/queue/notifications", payload);
    }
    
    public void sendAttendanceUpdate(Object payload) {
        messagingTemplate.convertAndSend("/queue/attendance", payload);
    }
    
    public void sendLocationUpdate(Object payload) {
        messagingTemplate.convertAndSend("/queue/locations", payload);
    }
    
    public void publish(String destination, Object payload) {
        messagingTemplate.convertAndSend(destination, payload);
    }
}
