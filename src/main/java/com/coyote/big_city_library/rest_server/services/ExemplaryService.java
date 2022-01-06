package com.coyote.big_city_library.rest_server.services;

import java.util.List;

import com.coyote.big_city_library.rest_server.dao.entities.Exemplary;
import com.coyote.big_city_library.rest_server.dao.repositories.ExemplaryRepository;
import com.coyote.big_city_library.rest_server.dto.ExemplaryDto;
import com.coyote.big_city_library.rest_server.dto.ExemplaryMapper;

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

    @Autowired
    protected ExemplaryMapper exemplaryMapper;

    /**
     * Adds a new given exemplary.
     * 
     * @param exemplaryDto to add.
     * @return The added exemplary; will never be null.
     * @see Exemplary
     * @see ExemplaryDto
     */
    public ExemplaryDto addExemplary(ExemplaryDto exemplaryDto) {
        Exemplary exemplary = exemplaryMapper.toModel(exemplaryDto);
        exemplary =  exemplaryRepository.save(exemplary);

        return exemplaryMapper.toDto(exemplary);
    }

    /**
     * Returns a list of all the exemplaries.
     * 
     * @return All the exemplaries.
     * @see Exemplary
     * @see ExemplaryDto
     */
    public List<ExemplaryDto> findAllExemplaries() {
        return exemplaryMapper.toDto(exemplaryRepository.findAll());
    }

    /**
     * Returns a exemplary with a given id.
     * 
     * @param id of a exemplary.
     * @return The exemplary with the given id or null if none found.
     * @see Exemplary
     * @see ExemplaryDto
     */
    public ExemplaryDto findExemplaryById(Integer id) {
        return exemplaryMapper.toDto(exemplaryRepository.findById(id).orElse(null));
    }

    /**
     * Updates a given exemplary.
     * 
     * @param exemplaryDto to update.
     * @return The updated exemplary; will never be null.
     * @see Exemplary
     * @see ExemplaryDto
     */
    public ExemplaryDto updateExemplary(ExemplaryDto exemplaryDto) {
        Exemplary exemplary = exemplaryMapper.toModel(exemplaryDto);
        return exemplaryMapper.toDto(exemplaryRepository.save(exemplary));
    }

    /**
     * Deletes a given exemplary.
     * 
     * @param exemplaryDto to delete.
     * @see Exemplary
     * @see ExemplaryDto
     */
    public void deleteExemplary(ExemplaryDto exemplaryDto) {
        exemplaryRepository.delete(exemplaryMapper.toModel(exemplaryDto));
    }

    /**
     * Deletes a exemplary with a given id
     * 
     * @param id of a exemplary.
     * @see Exemplary
     * @see ExemplaryDto
     */
    public void deleteExemplaryById(Integer id) {
        exemplaryRepository.deleteById(id);
    }

}
