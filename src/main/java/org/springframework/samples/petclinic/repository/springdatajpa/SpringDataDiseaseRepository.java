package org.springframework.samples.petclinic.repository.springdatajpa;

import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Disease;
import org.springframework.samples.petclinic.repository.DiseaseRepository;

public interface SpringDataDiseaseRepository extends DiseaseRepository, Repository<Disease, Integer> {

}