package com.sbhuvana.pubsub.gateways;

import com.sbhuvana.pubsub.models.BrokerMessage;
import com.sbhuvana.pubsub.models.PublisherMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Service
public class PubSubGatewayService {
    private final Logger logger = LoggerFactory.getLogger(PubSubGatewayService.class);
    private final String PORT = "8082";
    private final RestTemplate restTemplate;

    public PubSubGatewayService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void pingHost(String hostname) {
        final var url = "http://" + hostname + ":" + PORT + "/health-check";
        try {
            final var response = this.restTemplate.getForEntity(url, String.class);
            this.logger.info("Host [{}] responded with [{}]", hostname, response.getBody());
        } catch (ResourceAccessException exception) {
            this.logger.error("Host [{}] couldn't be connected because [{}]", hostname, exception.getMessage());
        }
    }

    public void publish(String hostname, PublisherMessage message) {
        final var url = "http://" + hostname + ":" + PORT + "/publish";
        this.restTemplate.postForEntity(url, message, String.class);
    }

    public void forwardBrokerMessage(String hostname, BrokerMessage brokerMessage) {
        final var url = "http://" + hostname + ":" + PORT + "/broker";
        this.restTemplate.postForEntity(url, brokerMessage, String.class);
    }
}
