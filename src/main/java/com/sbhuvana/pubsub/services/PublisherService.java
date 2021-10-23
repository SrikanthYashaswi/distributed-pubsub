package com.sbhuvana.pubsub.services;

import com.sbhuvana.pubsub.models.PublisherMessage;
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
