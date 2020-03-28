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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Disease;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class DiseaseServiceTests {

	private static final int TEST_DISEASE_ID_POSITIVE = 1;
	private static final int TEST_DISEASE_ID_NEGATIVE = 1;

	@Autowired
	protected DiseaseService diseaseService;

	@Test
	void shouldFindDiseaseById() {
		Disease disease = this.diseaseService.findDiseaseById(TEST_DISEASE_ID_POSITIVE);
		assertThat(disease.getId()).isEqualTo(1);

	}

	@Test
	void shouldFindDiseaseAll() {

		Collection<Disease> diseases = this.diseaseService.findAll();

		Disease disease1 = EntityUtils.getById(diseases, Disease.class, 1);
		assertThat(disease1.getCure()).isEqualTo("malisimo de la muerte");
		Disease disease3 = EntityUtils.getById(diseases, Disease.class, 4);
		assertThat(disease3.getSeverity()).isEqualTo("MEDIUM");

	}

	@Test
	void shouldInsertDisease() {
		Disease diseases = this.diseaseService.findDiseaseById(1);
		Collection<Disease> found = this.diseaseService.findAll();
		int count = found.size();

		final Disease disease = new Disease();
		final Pet pet = new Pet();
		pet.setId(1);
		disease.setSeverity("LOW");
		disease.setCure("cure");
		disease.setSymptoms("esta mal");
		disease.setPet(pet);

		this.diseaseService.saveDisease(disease);
		assertThat(disease.getId().longValue()).isNotEqualTo(0);

		diseases = this.diseaseService.findDiseaseById(1);
		assertThat(diseases.getId().longValue()).isNotEqualTo(count + 1);
	}

	@Test
	@Transactional
	void shouldUpdateOwner() {
		Disease disease = this.diseaseService.findDiseaseById(1);
		String oldSymptoms = disease.getSymptoms();
		String newSymptoms = oldSymptoms + "XXXXXXXX";

		disease.setSymptoms(newSymptoms);
		this.diseaseService.saveDisease(disease);
		// retrieving new symptoms from database
		disease = this.diseaseService.findDiseaseById(1);
		assertThat(disease.getSymptoms()).isEqualTo(newSymptoms);
		
	}

	@Test
	void shouldDeletetDisease() {
		Collection<Disease> found1 = this.diseaseService.findAll();
		int count1 = found1.size();
		Disease disease1 = this.diseaseService.findDiseaseById(1);
		this.diseaseService.delete(disease1);
		Collection<Disease> found2 = this.diseaseService.findAll();
		int count2 = found2.size();
		assertTrue("Algo pasa...", count2 < count1);


	}

}