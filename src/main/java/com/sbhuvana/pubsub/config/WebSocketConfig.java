package com.sbhuvana.pubsub.config;

import com.sbhuvana.pubsub.handlers.SubscriberHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final SubscriberHandler subscriberHandler;

    public WebSocketConfig(SubscriberHandler subscriberHandler) {
        this.subscriberHandler = subscriberHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(subscriberHandler, "/app/**").setAllowedOriginPatterns("*");
    }
}
