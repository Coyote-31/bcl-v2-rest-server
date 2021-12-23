package com.coyote.big_city_library.rest_server.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.coyote.big_city_library.rest_server.dao.entities.Publisher;
import com.coyote.big_city_library.rest_server.services.PublisherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/publisher")
public class PublisherController {

    @Autowired
    PublisherService publisherService;

    @PostMapping("/add")
    public Publisher addPublisher(@Valid @RequestBody Publisher publisher) {
        Publisher publisherSaved = publisherService.addPublisher(publisher);
        log.debug("addPublisher() => publisher with name '{}' added", publisherSaved.getName());
        return publisherSaved;
    }

    @GetMapping("/all")
    public List<Publisher> findAllPublishers() {
        List<Publisher> publishers = publisherService.findAllPublishers();
        log.debug("findAllPublishers() => {} publisher(s) found", publishers.size());
        return publishers;
    }
    
    @GetMapping("/{id}")
    public Publisher findPublisherById(@PathVariable Integer id) {
        Optional<Publisher> optionalPublisher = publisherService.findPublisherById(id);
        Publisher publisher;
        if (optionalPublisher.isPresent()) {
            publisher = optionalPublisher.get();
            log.debug("findPublisherById() => publisher with name '{}' found", publisher.getName());
        } else {
            publisher = null;
            log.debug("findPublisherById() => No publisher found with id '{}'", id);
        }
        return publisher;
    }

    @PutMapping("/update")
    public Publisher updatePublisher(@Valid @RequestBody Publisher publisher) {
        Publisher publisherUpdated = publisherService.updatePublisher(publisher);
        log.debug("updatePublisher() => publisher with name '{}' updated", publisherUpdated.getName());
        return publisherUpdated;
    }

    @DeleteMapping("/delete")
    public void deletePublisher(@Valid @RequestBody Publisher publisher) {
        publisherService.deletePublisher(publisher);
        log.debug("deletePublisher() => publisher with name '{}' removed", publisher.getName());
    }

    @DeleteMapping("/delete/{id}")
    public void deletePublisherById(@PathVariable Integer id) {
        publisherService.deletePublisherById(id);
        log.debug("deletePublisherById() => publisher with id '{}' removed", id);
    }

}
