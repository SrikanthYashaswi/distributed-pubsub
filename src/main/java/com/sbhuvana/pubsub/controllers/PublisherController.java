package com.sbhuvana.pubsub.controllers;

import com.sbhuvana.pubsub.models.PublisherMessage;
import com.sbhuvana.pubsub.services.PublisherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/publish")
public class PublisherController {

    public final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @PostMapping()
    public ResponseEntity create(@RequestBody PublisherMessage publisherMessage) {
        this.publisherService.publish(publisherMessage);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}