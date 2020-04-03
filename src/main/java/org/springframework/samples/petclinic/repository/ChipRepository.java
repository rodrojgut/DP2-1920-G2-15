package org.springframework.samples.petclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Chip;

public interface ChipRepository {

	Chip findById(int id) throws DataAccessException;

	void save(Chip chip) throws DataAccessException;
	
	void delete(Chip chip) throws DataAccessException;
}

