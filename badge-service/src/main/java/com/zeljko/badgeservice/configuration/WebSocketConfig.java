package com.zeljko.badgeservice.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        // Register the "/ws" endpoint, enabling the SockJS protocol.
        // SockJS is used (both client and server side) to allow alternative
        // messaging options if WebSocket is not available.
        registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();

    }
}
