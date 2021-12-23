package com.coyote.big_city_library.rest_server.services;

import java.util.List;
import java.util.Optional;

import com.coyote.big_city_library.rest_server.dao.entities.Exemplary;
import com.coyote.big_city_library.rest_server.dao.repositories.ExemplaryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *  Service class handling exemplaries
 * 
 * @see ExemplaryRepository
 */
@Component
public class ExemplaryService {
    
    @Autowired
    private ExemplaryRepository exemplaryRepository;

    /**
     * Adds a new given exemplary.
     * 
     * @param exemplary to add.
     * @return The added exemplary; will never be null.
     * @see Exemplary
     */
    public Exemplary addExemplary(Exemplary exemplary) {
        return exemplaryRepository.save(exemplary);
    }

    /**
     * Returns a list of all the exemplaries.
     * 
     * @return All the exemplaries.
     * @see Exemplary
     */
    public List<Exemplary> findAllExemplaries() {
        return exemplaryRepository.findAll();
    }

    /**
     * Returns a exemplary with a given id.
     * 
     * @param id of a exemplary.
     * @return The exemplary with the given id or Optional#empty() if none found.
     */
    public Optional<Exemplary> findExemplaryById(Integer id) {
        return exemplaryRepository.findById(id);
    }

    /**
     * Updates a given exemplary.
     * 
     * @param exemplary to update.
     * @return The updated exemplary; will never be null.
     * @see Exemplary
     */
    public Exemplary updateExemplary(Exemplary exemplary) {
        return exemplaryRepository.save(exemplary);
    }

    /**
     * Deletes a given exemplary.
     * 
     * @param exemplary to delete.
     * @see Exemplary
     */
    public void deleteExemplary(Exemplary exemplary) {
        exemplaryRepository.delete(exemplary);
    }

    /**
     * Deletes a exemplary with a given id
     * 
     * @param id of a exemplary.
     */
    public void deleteExemplaryById(Integer id) {
        exemplaryRepository.deleteById(id);
    }

}
