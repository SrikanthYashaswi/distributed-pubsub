package com.sbhuvana.pubsub.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbhuvana.pubsub.models.MessageEvent;
import com.sbhuvana.pubsub.models.PublisherMessage;
import com.sbhuvana.pubsub.models.SubscriberMessage;
import com.sbhuvana.pubsub.services.UserSubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Component
public class SubscriberHandler extends TextWebSocketHandler {
    private final Logger logger = LoggerFactory.getLogger(SubscriberHandler.class);
    private final UserSubscriptionService userSubscriptionService;
    private final ObjectMapper mapper;

    public SubscriberHandler(UserSubscriptionService userSubscriptionService, ObjectMapper mapper) {
        this.userSubscriptionService = userSubscriptionService;
        this.mapper = mapper;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws IOException {
        final var message = mapper.readValue(textMessage.getPayload(), SubscriberMessage.class);

        if (message.event.equals(MessageEvent.SUBSCRIBE)) {
            this.userSubscriptionService.subscribe(session, message.message);
            this.logger.info("New subscription to topic - [{}]", message.message);
        }
        if (message.event.equals(MessageEvent.UNSUBSCRIBE)) {
            this.userSubscriptionService.unSubscribe(session.getId(), message.message);
            this.logger.info("User unsubscribed from topic - [{}]", message.message);
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        final var advertisements = String.join(",", this.userSubscriptionService.getAdvertisements());
        final var publisherMessage = new PublisherMessage("pubsub:system", "advertisement", advertisements);
        session.sendMessage(new TextMessage(mapper.writeValueAsString(publisherMessage)));
    }
}
