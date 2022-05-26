package com.shrk.pubsub.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "network")
public class NetworkConfig {
    private String neighbours;
    private final List<String> brokerServingTopics = new ArrayList<>();

    public List<String> getNeighbours() {
        if (neighbours == null) {
            return List.of();
        }
        return Arrays.asList(neighbours.split(","));
    }

    public void setNeighbours(String neighbours) {
        this.neighbours = neighbours;
    }

    public String getHostName() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            return inetAddress.getHostName();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public void addBrokerServingTopics(String topic) {
        this.brokerServingTopics.add(topic);
    }

    public List<String> getBrokerServingTopics() {
        return brokerServingTopics;
    }
}
