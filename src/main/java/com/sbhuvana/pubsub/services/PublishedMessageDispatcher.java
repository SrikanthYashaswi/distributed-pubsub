package com.sbhuvana.pubsub.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
@EnableAsync
public class PublishedMessageDispatcher {

    private final Logger logger = LoggerFactory.getLogger(PublishedMessageDispatcher.class);
    private final PublisherQueueService publisherQueueService;
    private final UserSubscriptionService subscriptionService;
    private final BrokerNetworkSubscriptionService brokerNetworkSubscriptionService;
    private final ObjectMapper objectMapper;
    private final Set<String> acknowledgedMessageIds;

    public PublishedMessageDispatcher(PublisherQueueService publisherQueueService,
                                      UserSubscriptionService userSubscriptionService,
                                      BrokerNetworkSubscriptionService brokerNetworkSubscriptionService,
                                      ObjectMapper objectMapper) {
        this.publisherQueueService = publisherQueueService;
        this.subscriptionService = userSubscriptionService;
        this.brokerNetworkSubscriptionService = brokerNetworkSubscriptionService;
        this.acknowledgedMessageIds = new HashSet<>();
        this.objectMapper = objectMapper;
    }

    @Scheduled(fixedRate = 10)
    public void dispatchedQueuedMessages() throws InterruptedException, IOException {
        final var optionalMessage = this.publisherQueueService.dequeue();
        if (optionalMessage.isEmpty()) {
            return;
        }
        final var message = optionalMessage.get();
        this.brokerNetworkSubscriptionService.forwardToBrokersWithTopic(message.topic, message);
        this.subscriptionService.notifyTopicSubscribers(message.topic, new TextMessage(this.objectMapper.writeValueAsString(message)));
    }
}
