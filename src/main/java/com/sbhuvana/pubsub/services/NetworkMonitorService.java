package com.sbhuvana.pubsub.services;

import com.sbhuvana.pubsub.config.NetworkConfig;
import com.sbhuvana.pubsub.gateways.PubSubGatewayService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NetworkMonitorService {

    private final NetworkConfig networkConfig;
    private final PubSubGatewayService pubSubGatewayService;

    public NetworkMonitorService(NetworkConfig networkConfig, PubSubGatewayService pubSubGatewayService) {
        this.networkConfig = networkConfig;
        this.pubSubGatewayService = pubSubGatewayService;
    }

    //@Scheduled(initialDelay = 0, fixedRate = 30000)
    public void pingNeighbours() {
        List<String> hostList = this.networkConfig.getNeighbours();
        for (String host : hostList) {
            this.pubSubGatewayService.pingHost(host);
        }
    }
}
