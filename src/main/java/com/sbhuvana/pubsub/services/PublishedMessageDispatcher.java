package com.sbhuvana.pubsub.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;

@Component
@EnableAsync
public class PublishedMessageDispatcher {
    private final PublisherQueueService publisherQueueService;
    private final SubscriptionService subscriptionService;
    private final ObjectMapper objectMapper;

    public PublishedMessageDispatcher(PublisherQueueService publisherQueueService, SubscriptionService subscriptionService, ObjectMapper objectMapper) {
        this.publisherQueueService = publisherQueueService;
        this.subscriptionService = subscriptionService;
        this.objectMapper = objectMapper;
    }

    @Scheduled(fixedRate = 10)
    public void dispatchedQueuedMessages() throws InterruptedException, IOException {
        final var optionalMessage = this.publisherQueueService.dequeue();
        if (optionalMessage.isEmpty()) {
            return;
        }
        final var message = optionalMessage.get();
        this.subscriptionService.notifyTopicSubscribers(message.topic, new TextMessage(this.objectMapper.writeValueAsString(message)));
    }
}
