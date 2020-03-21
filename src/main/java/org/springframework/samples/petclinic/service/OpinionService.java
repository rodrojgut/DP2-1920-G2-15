package org.springframework.samples.petclinic.service;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Opinion;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.repository.OpinionRepository;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.samples.petclinic.repository.UserRepository;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * OpinionService
 */
@Service
public class OpinionService {

    @Autowired
    private OpinionRepository opinionRepo;

    @Autowired
    private VetRepository vetRepo;

    @Autowired
    private OwnerRepository ownerRepo;

    @Autowired
    private UserRepository userRepo;

	

    public OpinionService(OpinionRepository repository, VetRepository vetRepo,OwnerRepository ownerRepo, UserRepository userRepo) {
        this.opinionRepo = repository;
        this.vetRepo = vetRepo;
        this.ownerRepo = ownerRepo;
        this.userRepo = userRepo;
    }

    public Optional<Opinion> findOpinionById(Integer id){
        return this.opinionRepo.findById(id);
    }

	public void saveOpinion(@Valid Opinion opinion) {
        this.opinionRepo.save(opinion);

    }
    
    public Vet getVetById(Integer vetId){
       return this.vetRepo.findById(vetId);
    }

    public Owner getOwnerById(Integer ownerId){
        return this.ownerRepo.findById(ownerId);
    }

	public User getCurrentUser() {        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();             //Obtiene el nombre del ususario actual
         return this.userRepo.findByUsername(currentPrincipalName);         //Obtiene el usuario con ese nombre
                                   
      
	}



    
}