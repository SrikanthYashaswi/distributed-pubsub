package com.shrk.pubsub.services;

import com.shrk.pubsub.models.PublisherMessage;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class PublisherQueueService {
    private final Queue<PublisherMessage> queue = new ConcurrentLinkedQueue<>();

    public void enque(PublisherMessage message) {
        this.queue.add(message);
    }

    public Optional<PublisherMessage> dequeue() {
        return Optional.ofNullable(this.queue.poll());
    }

}
