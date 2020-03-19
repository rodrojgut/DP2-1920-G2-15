
package org.springframework.samples.petclinic.repository.springdatajpa;

import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Chip;
import org.springframework.samples.petclinic.repository.ChipRepository;

public interface SpringDataChipRepository extends ChipRepository, Repository<Chip, Integer> {

}
