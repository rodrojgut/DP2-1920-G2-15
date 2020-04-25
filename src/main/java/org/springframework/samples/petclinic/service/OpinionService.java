
package org.springframework.samples.petclinic.service;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Opinion;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.samples.petclinic.repository.UserRepository;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.samples.petclinic.repository.springdatajpa.SpringDataOpinionRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * OpinionService
 */
@Service
public class OpinionService {

	
	private SpringDataOpinionRepository	opinionRepo;
	
	private VetRepository				vetRepo;

	private OwnerRepository				ownerRepo;

	private UserRepository				userRepo;

	@Autowired
	public OpinionService(final SpringDataOpinionRepository repository, final VetRepository vetRepo, final OwnerRepository ownerRepo, final UserRepository userRepo) {
		this.opinionRepo = repository;
		this.vetRepo = vetRepo;
		this.ownerRepo = ownerRepo;
		this.userRepo = userRepo;
	}

	public Optional<Opinion> findOpinionById(final Integer id) {
		return this.opinionRepo.findById(id);
	}

	public void saveOpinion(@Valid final Opinion opinion) {
		this.opinionRepo.save(opinion);

	}

	public Vet getVetById(final Integer vetId) {
		return this.vetRepo.findById(vetId);
	}

	public User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();             //Obtiene el nombre del ususario actual
		return this.userRepo.findByUsername(currentPrincipalName);         //Obtiene el usuario con ese nombre

	}

	@Transactional
	public Iterable<Opinion> findAll() {
		return this.opinionRepo.findAll();
	}

	@Transactional
	public Iterable<Opinion> findAllMine(final String username) {
		return this.opinionRepo.findAllMine(username);
	}

	@Transactional
	public void deleteOpinion(final Opinion opinion) {
		opinion.getVet().removeOpinion(opinion);
		//opinion.getUser().removeOpinion(opinion);
		this.opinionRepo.delete(opinion);
	}
}
