package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Opinion;

/**
 * OpinionRepository
 */
public interface OpinionRepository extends CrudRepository<Opinion,Integer> {

    
}