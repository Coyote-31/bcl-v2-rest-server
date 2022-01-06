package com.coyote.big_city_library.rest_server.controllers;

import java.util.List;

import javax.validation.Valid;

import com.coyote.big_city_library.rest_server.dto.PublisherDto;
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
    public PublisherDto addPublisher(@Valid @RequestBody PublisherDto publisherDto) {
        PublisherDto publisherSaved = publisherService.addPublisher(publisherDto);
        log.debug("addPublisher() => publisher with name '{}' added", publisherSaved.getName());
        return publisherSaved;
    }

    @GetMapping("/all")
    public List<PublisherDto> findAllPublishers() {
        List<PublisherDto> publishers = publisherService.findAllPublishers();
        log.debug("findAllPublishers() => {} publisher(s) found", publishers.size());
        return publishers;
    }
    
    @GetMapping("/{id}")
    public PublisherDto findPublisherById(@PathVariable Integer id) {
        PublisherDto publisherDto = publisherService.findPublisherById(id);
        if (publisherDto != null) {
            log.debug("findPublisherById() => publisher with name '{}' found", publisherDto.getName());
        } else {
            log.debug("findPublisherById() => No publisher found with id '{}'", id);
        }
        return publisherDto;
    }

    @PutMapping("/update")
    public PublisherDto updatePublisher(@Valid @RequestBody PublisherDto publisherDto) {
        PublisherDto publisherUpdated = publisherService.updatePublisher(publisherDto);
        log.debug("updatePublisher() => publisher with name '{}' updated", publisherUpdated.getName());
        return publisherUpdated;
    }

    @DeleteMapping("/delete")
    public void deletePublisher(@Valid @RequestBody PublisherDto publisherDto) {
        publisherService.deletePublisher(publisherDto);
        log.debug("deletePublisher() => publisher with name '{}' removed", publisherDto.getName());
    }

    @DeleteMapping("/delete/{id}")
    public void deletePublisherById(@PathVariable Integer id) {
        publisherService.deletePublisherById(id);
        log.debug("deletePublisherById() => publisher with id '{}' removed", id);
    }

}
