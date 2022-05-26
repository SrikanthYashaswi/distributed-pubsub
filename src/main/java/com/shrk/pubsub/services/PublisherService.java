package com.shrk.pubsub.services;

import com.shrk.pubsub.models.PublisherMessage;
import org.springframework.stereotype.Service;

@Service
public class PublisherService {
    private final PublisherQueueService publisherQueueService;

    public PublisherService(PublisherQueueService publisherQueueService) {
        this.publisherQueueService = publisherQueueService;
    }

    public void publish(PublisherMessage message) {
        this.publisherQueueService.enque(message);
    }
}
