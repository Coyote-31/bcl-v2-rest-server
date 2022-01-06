package com.coyote.big_city_library.rest_server.controllers;

import java.util.List;

import javax.validation.Valid;

import com.coyote.big_city_library.rest_server.dto.ExemplaryDto;
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
    public ExemplaryDto addExemplary(@Valid @RequestBody ExemplaryDto exemplaryDto) {
        ExemplaryDto exemplarySaved = exemplaryService.addExemplary(exemplaryDto);
        log.debug("addExemplary() => exemplary with id '{}' added", exemplarySaved.getId());
        return exemplarySaved;
    }

    @GetMapping("/all")
    public List<ExemplaryDto> findAllExemplaries() {
        List<ExemplaryDto> exemplaries = exemplaryService.findAllExemplaries();
        log.debug("findAllExemplaries() => {} exemplary(s) found", exemplaries.size());
        return exemplaries;
    }
    
    @GetMapping("/{id}")
    public ExemplaryDto findExemplaryById(@PathVariable Integer id) {
        ExemplaryDto exemplaryDto = exemplaryService.findExemplaryById(id);
        if (exemplaryDto != null) {
            log.debug("findExemplaryById() => exemplary with id '{}' found", exemplaryDto.getId());
        } else {
            log.debug("findExemplaryById() => No exemplary found with id '{}'", id);
        }
        return exemplaryDto;
    }

    @PutMapping("/update")
    public ExemplaryDto updateExemplary(@Valid @RequestBody ExemplaryDto exemplaryDto) {
        ExemplaryDto exemplaryUpdated = exemplaryService.updateExemplary(exemplaryDto);
        log.debug("updateExemplary() => exemplary with id '{}' updated", exemplaryUpdated.getId());
        return exemplaryUpdated;
    }

    @DeleteMapping("/delete")
    public void deleteExemplary(@Valid @RequestBody ExemplaryDto exemplaryDto) {
        exemplaryService.deleteExemplary(exemplaryDto);
        log.debug("deleteExemplary() => exemplary with id '{}' removed", exemplaryDto.getId());
    }

    @DeleteMapping("/delete/{id}")
    public void deleteExemplaryById(@PathVariable Integer id) {
        exemplaryService.deleteExemplaryById(id);
        log.debug("deleteExemplaryById() => exemplary with id '{}' removed", id);
    }

}
