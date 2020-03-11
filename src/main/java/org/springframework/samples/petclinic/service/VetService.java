/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.samples.petclinic.repository.UserRepository;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class VetService {

	private VetRepository vetRepository;

	private UserRepository userRepo;

	private OwnerRepository ownerRepo;


	@Autowired
	public VetService(VetRepository vetRepository, UserRepository userRepo, OwnerRepository ownerRepo) {
		this.vetRepository = vetRepository;
		this.userRepo = userRepo;
		this.ownerRepo = ownerRepo;
	}		

	@Transactional(readOnly = true)	
	public Collection<Vet> findVets() throws DataAccessException {
		return vetRepository.findAll();
	}

	//Obtinene el Owner logeado actuakmente (si es un Owner)
	public Owner getActualOwner() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
		User aux = this.userRepo.findByUsername(currentPrincipalName);
		return this.ownerRepo.findByUser(aux);
	}	

}
