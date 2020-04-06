package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Chip;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.repository.ChipRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChipService {

	private ChipRepository chipRepository;

	@Autowired
	public ChipService(ChipRepository chipRepository) {
		this.chipRepository = chipRepository;
	}

	@Transactional(readOnly = true)
	public Chip findChipById(int id) throws DataAccessException {
		return chipRepository.findById(id);
  }
    
	@Transactional
	public void saveChip(Chip chip) throws DataAccessException {
		this.chipRepository.save(chip);
	}
	
	@Transactional
	public void deleteChip(Chip chip) throws DataAccessException {
		this.chipRepository.delete(chip);
	}
}
