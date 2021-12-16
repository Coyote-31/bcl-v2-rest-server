package com.coyote.big_city_library.rest_server.services;

import java.util.List;
import java.util.Optional;

import com.coyote.big_city_library.rest_server.dao.entities.Publisher;
import com.coyote.big_city_library.rest_server.dao.repositories.PublisherRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *  Service class handling publishers
 * 
 * @see PublisherRepository
 */
@Component
public class PublisherService {
    
    @Autowired
    private PublisherRepository publisherRepository;

    /**
     * Saves the given publisher. 
     * Update or Create if the publisher doesn't exist.
     * 
     * @param publisher
     * @see Publisher
     */
    public Publisher savePublisher(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    /**
     * Returns a list of all the publishers.
     * 
     * @return All the publishers.
     * @see Publisher
     */
    public List<Publisher> findAllPublishers() {
        return publisherRepository.findAll();
    }

    /**
     * Returns a publisher with a given id.
     * 
     * @param id of a publisher.
     * @return The publisher with the given id or Optional#empty() if none found.
     */
    public Optional<Publisher> findPublisherById(Integer id) {
        return publisherRepository.findById(id);
    }

    /**
     * Deletes a given publisher.
     * 
     * @param publisher to delete.
     * @see Publisher
     */
    public void deletePublisher(Publisher publisher) {
        publisherRepository.delete(publisher);
    }

    /**
     * Deletes a publisher with a given id
     * 
     * @param id of a publisher.
     */
    public void deletePublisherById(Integer id) {
        publisherRepository.deleteById(id);
    }

}
