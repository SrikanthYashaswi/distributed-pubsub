package com.shrk.pubsub.controllers;


import com.shrk.pubsub.config.NetworkConfig;
import com.shrk.pubsub.models.BrokerMessage;
import com.shrk.pubsub.models.MessageEvent;
import com.shrk.pubsub.services.SubscriptionRoutingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/broker")
public class BrokerController {

    private final Logger logger = LoggerFactory.getLogger(BrokerController.class);
    private final SubscriptionRoutingService subscriptionRoutingService;
    private final NetworkConfig networkConfig;

    public BrokerController(SubscriptionRoutingService subscriptionRoutingService, NetworkConfig networkConfig) {
        this.subscriptionRoutingService = subscriptionRoutingService;
        this.networkConfig = networkConfig;
    }

    @PostMapping()
    public ResponseEntity create(@RequestBody BrokerMessage brokerMessage) {
        if (brokerMessage.getMessageEvent().equals(MessageEvent.SUBSCRIBE)) {
            this.subscriptionRoutingService.receiveSubscriptionEvent(brokerMessage);
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("/advertise")
    public ResponseEntity advertise(@RequestBody BrokerMessage brokerMessage) {
        this.logger.info("Received Advertise for Topic [{}]", brokerMessage.getMessage());
        if (brokerMessage.getMessageEvent().equals(MessageEvent.ADVERTISE)) {
            this.networkConfig.addBrokerServingTopics(brokerMessage.getMessage());
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
