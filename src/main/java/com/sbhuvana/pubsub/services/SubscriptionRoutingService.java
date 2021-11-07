package com.sbhuvana.pubsub.services;


import com.sbhuvana.pubsub.config.NetworkConfig;
import com.sbhuvana.pubsub.gateways.PubSubGatewayService;
import com.sbhuvana.pubsub.models.BrokerMessage;
import com.sbhuvana.pubsub.models.MessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * SubscriptionRoutingService is responsible for dispatching events of user's subscriptions to other brokers
 */
@Service
public class SubscriptionRoutingService {
    private final Logger logger = LoggerFactory.getLogger(SubscriptionRoutingService.class);
    private final NetworkConfig networkConfig;
    private final PubSubGatewayService pubSubGatewayService;
    private final Set<String> acknowledgedBrokerMessageIds = new HashSet<>();
    private final BrokerNetworkSubscriptionService brokerNetworkSubscriptionService;


    public SubscriptionRoutingService(NetworkConfig networkConfig, PubSubGatewayService pubSubGatewayService,
                                      BrokerNetworkSubscriptionService brokerNetworkSubscriptionService) {
        this.networkConfig = networkConfig;
        this.pubSubGatewayService = pubSubGatewayService;
        this.brokerNetworkSubscriptionService = brokerNetworkSubscriptionService;
    }

    public void forwardSubscriptionEvent(String topic) {
        final var brokerMessage = new BrokerMessage(MessageEvent.SUBSCRIBE, topic, networkConfig.getHostName());
        this.forwardBrokerMessage(brokerMessage);
    }

    public void receiveSubscriptionEvent(BrokerMessage message) {
        if (acknowledgedBrokerMessageIds.contains(message.getId())) {
            this.logger.info("0. Ignoring message id [{}] as this broker has already acknowledged the message", message.getId());
            return;
        }
        this.logger.info("2. Received event [{}] topic [{}] message [{}] from source [{}]", message.getMessageEvent(), message.getMessage(), message.getId(), message.getHost());
        if (networkConfig.getBrokerServingTopics().contains(message.getMessage())) {
            this.logger.info("3. Arrived at destination broker [{}] topic [{}] message [{}] from source [{}]", message.getMessageEvent(), message.getMessage(), message.getId(), message.getHost());
            this.brokerNetworkSubscriptionService.subscribe(message.getHost(), message.getMessage());
        } else {
            this.forwardBrokerMessage(message);
        }
    }

    private void forwardBrokerMessage(BrokerMessage brokerMessage) {
        this.logger.info("1. Forwarding [{}] event with topic [{}] with message id [{}] to neighbours", brokerMessage.getMessageEvent().toString(), brokerMessage.getMessage(), brokerMessage.getId());
        this.acknowledgedBrokerMessageIds.add(brokerMessage.getId());
        final var hosts = this.networkConfig.getNeighbours();
        for (String host : hosts) {
            this.pubSubGatewayService.forwardBrokerMessage(host, brokerMessage);
        }
    }
}
