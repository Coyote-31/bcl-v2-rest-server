package com.coyote.big_city_library.rest_server.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.coyote.big_city_library.rest_server.dao.entities.Exemplary;
import com.coyote.big_city_library.rest_server.services.ExemplaryService;

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
@RequestMapping("/exemplary")
public class ExemplaryController {

    @Autowired
    ExemplaryService exemplaryService;

    @PostMapping("/add")
    public Exemplary addExemplary(@Valid @RequestBody Exemplary exemplary) {
        Exemplary exemplarySaved = exemplaryService.addExemplary(exemplary);
        log.debug("addExemplary() => exemplary with id '{}' added", exemplarySaved.getId());
        return exemplarySaved;
    }

    @GetMapping("/all")
    public List<Exemplary> findAllExemplaries() {
        List<Exemplary> exemplaries = exemplaryService.findAllExemplaries();
        log.debug("findAllExemplaries() => {} exemplary(s) found", exemplaries.size());
        return exemplaries;
    }
    
    @GetMapping("/{id}")
    public Exemplary findExemplaryById(@PathVariable Integer id) {
        Optional<Exemplary> optionalExemplary = exemplaryService.findExemplaryById(id);
        Exemplary exemplary;
        if (optionalExemplary.isPresent()) {
            exemplary = optionalExemplary.get();
            log.debug("findExemplaryById() => exemplary with id '{}' found", exemplary.getId());
        } else {
            exemplary = null;
            log.debug("findExemplaryById() => No exemplary found with id '{}'", id);
        }
        return exemplary;
    }

    @PutMapping("/update")
    public Exemplary updateExemplary(@Valid @RequestBody Exemplary exemplary) {
        Exemplary exemplaryUpdated = exemplaryService.updateExemplary(exemplary);
        log.debug("updateExemplary() => exemplary with id '{}' updated", exemplaryUpdated.getId());
        return exemplaryUpdated;
    }

    @DeleteMapping("/delete")
    public void deleteExemplary(@Valid @RequestBody Exemplary exemplary) {
        exemplaryService.deleteExemplary(exemplary);
        log.debug("deleteExemplary() => exemplary with id '{}' removed", exemplary.getId());
    }

    @DeleteMapping("/delete/{id}")
    public void deleteExemplaryById(@PathVariable Integer id) {
        exemplaryService.deleteExemplaryById(id);
        log.debug("deleteExemplaryById() => exemplary with id '{}' removed", id);
    }

}
