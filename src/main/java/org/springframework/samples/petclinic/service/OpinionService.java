package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.repository.OpinionRepository;
import org.springframework.stereotype.Service;

/**
 * OpinionService
 */
@Service
public class OpinionService {

    @Autowired
    private OpinionRepository repository;

    public OpinionService(OpinionRepository repository) {
        this.repository = repository;
    }
    

    
}