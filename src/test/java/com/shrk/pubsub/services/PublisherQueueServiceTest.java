package com.shrk.pubsub.services;

import org.junit.jupiter.api.Test;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.junit.jupiter.api.Assertions.*;

class PublisherQueueServiceTest {

    @Test
    public void doTest() {
        Queue<String> queue = new ConcurrentLinkedQueue<>();

        queue.add("name1");
        queue.add("name2");
        queue.add("name3");

        queue.forEach( q -> System.out.println(q));
        System.out.println("-------------------");
        queue.poll();
        queue.poll();
        queue.poll();
        queue.poll();
        queue.forEach( q -> System.out.println(q));



    }

}