package com.sbhuvana.pubsub.models;

import java.time.LocalDateTime;

public class PublisherMessage extends Message {
    public String topic;

    public PublisherMessage(String topic, String title, String content, String source, String sourceId, LocalDateTime publishedAt) {
        super(title, content, source, sourceId, publishedAt);
        this.topic = topic;
    }

    public PublisherMessage() {
        super();
    }

    public PublisherMessage(String topic, String title, String content) {
        super(title, content, null, null, null);
        this.topic = topic;
    }
}
