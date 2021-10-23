package com.sbhuvana.pubsub.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class SubscriptionService {
    private final Logger logger = LoggerFactory.getLogger(SubscriptionService.class);
    private final Map<String, List<WebSocketSession>> subscriptions = new HashMap<>();

    public void subscribe(WebSocketSession webSocketSession, String topic) {
        subscriptions.computeIfAbsent(topic, t -> new CopyOnWriteArrayList<>());
        subscriptions.get(topic).add(webSocketSession);
    }

    public void unSubscribe(String userId, String topic) {
        subscriptions.get(topic).removeIf(connection -> connection.getId().equals(userId));
    }

    public Set<String> getAdvertisements() {
        return this.subscriptions.keySet();
    }

    public void notifyTopicSubscribers(String topic, TextMessage message) {
        final var subscribers = this.subscriptions.getOrDefault(topic, List.of());
        for (WebSocketSession subscriber : subscribers) {
            try {
                if (subscriber.isOpen())
                    subscriber.sendMessage(message);
            } catch (IOException ex) {
                this.logger.error("Cant send message to [{}] as connection might be closed", subscriber.getId());
            }

        }
    }
}
