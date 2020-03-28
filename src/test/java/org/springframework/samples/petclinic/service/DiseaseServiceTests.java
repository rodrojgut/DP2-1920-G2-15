
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


	@Autowired
	protected DiseaseService diseaseService;

	@Test
	void shouldFindDiseaseById() {
		Disease disease = this.diseaseService.findDiseaseById(1);
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