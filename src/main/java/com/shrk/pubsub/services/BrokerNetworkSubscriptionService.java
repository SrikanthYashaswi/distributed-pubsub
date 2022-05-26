package com.shrk.pubsub.services;

import com.shrk.pubsub.gateways.PubSubGatewayService;
import com.shrk.pubsub.models.PublisherMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * This service is used to track topic assigned to this broker that are also served from other brokers.
 */
@Service
public class BrokerNetworkSubscriptionService {
    private final Logger logger = LoggerFactory.getLogger(BrokerNetworkSubscriptionService.class);
    private final HashMap<String, Set<String>> brokerInterestedTopics = new HashMap<>();
    private final PubSubGatewayService pubSubGatewayService;

    public BrokerNetworkSubscriptionService(PubSubGatewayService pubSubGatewayService) {
        this.pubSubGatewayService = pubSubGatewayService;
    }

    public void subscribe(String hostname, String topic) {
        brokerInterestedTopics.computeIfAbsent(topic, t -> ConcurrentHashMap.newKeySet());
        brokerInterestedTopics.get(topic).add(hostname);
        this.logger.info("Broker [{}] added to forward list for topic [{}]", hostname, topic);
    }

    public void forwardToBrokersWithTopic(String topic, PublisherMessage publisherMessage) {
        final var hosts = brokerInterestedTopics.getOrDefault(topic, Set.of());
        for (String host : hosts) {
            this.logger.info("Forwarding event to [{}] published on topic [{}]", host, topic);
            this.pubSubGatewayService.publish(host, publisherMessage);
        }
    }
}
