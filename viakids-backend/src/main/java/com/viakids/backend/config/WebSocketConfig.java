package com.viakids.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${app.rabbitmq.enabled:false}")
    private boolean rabbitMqEnabled;

    @Value("${spring.rabbitmq.host:localhost}")
    private String rabbitHost;

    @Value("${spring.rabbitmq.stomp-port:61613}")
    private int stompPort;

    @Value("${spring.rabbitmq.username:guest}")
    private String rabbitUser;

    @Value("${spring.rabbitmq.password:guest}")
    private String rabbitPass;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        if (rabbitMqEnabled) {
            config.enableStompBrokerRelay("/queue", "/topic")
                    .setRelayHost(rabbitHost)
                    .setRelayPort(stompPort)
                    .setClientLogin(rabbitUser)
                    .setClientPasscode(rabbitPass)
                    .setSystemLogin(rabbitUser)
                    .setSystemPasscode(rabbitPass);
        } else {
            config.enableSimpleBroker("/queue");
        }
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*");
    }
}
