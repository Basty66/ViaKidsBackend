package com.viakids.backend.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/location")
    @SendTo("/queue/locations")
    public Map<String, Object> sendLocation(Map<String, Object> location) {
        return location;
    }

    public void sendNotification(Object payload) {
        messagingTemplate.convertAndSend("/queue/notifications", payload);
    }

    public void sendAttendanceUpdate(Object payload) {
        messagingTemplate.convertAndSend("/queue/attendance", payload);
    }
}
