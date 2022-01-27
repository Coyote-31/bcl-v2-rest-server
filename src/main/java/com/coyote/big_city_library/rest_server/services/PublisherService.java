package com.coyote.big_city_library.rest_server.services;

import java.util.List;

import com.coyote.big_city_library.rest_server.dao.entities.Publisher;
import com.coyote.big_city_library.rest_server.dao.repositories.PublisherRepository;
import com.coyote.big_city_library.rest_server.dto.PublisherDto;
import com.coyote.big_city_library.rest_server.dto.PublisherMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *  Service class handling publishers
 * 
 * @see PublisherRepository
 */
@Service
public class PublisherService {
    
    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    protected PublisherMapper publisherMapper;

    /**
     * Adds a new given publisher.
     * 
     * @param publisherDto to add.
     * @return The added publisher; will never be null.
     * @see Publisher
     * @see PublisherDto
     */
    public PublisherDto addPublisher(PublisherDto publisherDto) {
        Publisher publisher = publisherMapper.toModel(publisherDto);
        publisher = publisherRepository.save(publisher);
        return publisherMapper.toDto(publisher);
    }

    /**
     * Returns a list of all the publishers.
     * 
     * @return All the publishers.
     * @see Publisher
     * @see PublisherDto
     */
    public List<PublisherDto> findAllPublishers() {
        return publisherMapper.toDto(publisherRepository.findAll());
    }

    /**
     * Returns a publisher with a given id.
     * 
     * @param id of a publisher.
     * @return The publisher with the given id or null if none found.
     * @see Publisher
     * @see PublisherDto
     */
    public PublisherDto findPublisherById(Integer id) {
        return publisherMapper.toDto(publisherRepository.findById(id).orElse(null));
    }

    /**
     * Updates a given publisher.
     * 
     * @param publisherDto to update.
     * @return The updated publisher; will never be null.
     * @see Publisher
     * @see PublisherDto
     */
    public PublisherDto updatePublisher(PublisherDto publisherDto) {
        Publisher publisher = publisherMapper.toModel(publisherDto);
        return publisherMapper.toDto(publisherRepository.save(publisher));
    }

    /**
     * Deletes a given publisher.
     * 
     * @param publisherDto to delete.
     * @see Publisher
     * @see PublisherDto
     */
    public void deletePublisher(PublisherDto publisherDto) {
        publisherRepository.delete(publisherMapper.toModel(publisherDto));
    }

    /**
     * Deletes a publisher with a given id
     * 
     * @param id of a publisher.
     * @see Publisher
     * @see PublisherDto
     */
    public void deletePublisherById(Integer id) {
        publisherRepository.deleteById(id);
    }

}
