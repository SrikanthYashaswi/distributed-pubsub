package com.sbhuvana.pubsub.models;

import java.time.LocalDateTime;
import java.util.List;

public class Message {
    protected String title;
    protected String content;
    protected String source;
    protected String sourceId;
    protected LocalDateTime publishedAt;
    protected List<String> topics;

    public Message() {

    }

    public Message(String title, String content, String source, String sourceId, LocalDateTime publishedAt) {
        this.title = title;
        this.content = content;
        this.source = source;
        this.sourceId = sourceId;
        this.publishedAt = publishedAt;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getSource() {
        return source;
    }

    public String getSourceId() {
        return sourceId;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public List<String> getTopics() {
        return topics;
    }
}
