package com.shrk.pubsub.models;

import java.util.UUID;

public class BrokerMessage {
    private String id;
    private MessageEvent messageEvent;
    private String host;
    private String message;

    public BrokerMessage(MessageEvent messageEvent, String message, String host) {
        this.id = UUID.randomUUID().toString();
        this.messageEvent = messageEvent;
        this.host = host;
        this.message = message;
    }

    public MessageEvent getMessageEvent() {
        return messageEvent;
    }

    public String getHost() {
        return host;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }
}
