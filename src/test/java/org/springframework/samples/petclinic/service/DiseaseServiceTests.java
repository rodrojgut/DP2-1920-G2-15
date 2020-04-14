
package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import java.util.Collection;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Disease;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class DiseaseServiceTests{


	public Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}
	
	@Autowired
	protected DiseaseService diseaseService;

	//FindbyId positive
	@Test
	void shouldFindDiseaseById() {
		Disease disease = this.diseaseService.findDiseaseById(1);
		assertThat(disease.getId()).isEqualTo(1);

	}
	//FindbyId negative
	@ParameterizedTest
	@ValueSource(ints = {-10,-40} )
	void shouldFindDiseaseByIdNegative(int argument){
		Assertions.assertThrows(NullPointerException.class,()->{this.diseaseService.findDiseaseById(argument).getClass();} );
	}

	@Test
	void shouldFindDiseaseAll() {

		Collection<Disease> diseases = this.diseaseService.findAll();

		Disease disease1 = EntityUtils.getById(diseases, Disease.class, 1);
		assertThat(disease1.getCure()).isEqualTo("malisimo de la muerte");
		Disease disease4 = EntityUtils.getById(diseases, Disease.class, 4);
		assertThat(disease4.getSeverity()).isEqualTo("MEDIUM");

	}
	
	@Test
	void shouldFindDiseaseNegativeAll() {

		Collection<Disease> diseases = this.diseaseService.findAll();

		Disease disease1 = EntityUtils.getById(diseases, Disease.class, 1);
		Disease disease4 = EntityUtils.getById(diseases, Disease.class, 4);
		diseases.add(disease1); diseases.add(disease4);
		Assertions.assertThrows(NullPointerException.class,()->{this.diseaseService.findDiseaseById(15).getSeverity().isEmpty();});
		

	}
	//Positive Insert
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
	//Negative Insert
	@Test
	public void shouldInsertNegativeDisease(){
		
		Disease d = new Disease(); 
		
		d.setCure("Probando cura");
		d.setSeverity("");
		d.setSymptoms("malisimo");
		String s = "LOW|MEDIUM|HIGH";
		Validator validator = createValidator();
		Set<ConstraintViolation<Disease>> constraintViolations =  validator.validate(d); 
		assertThat(constraintViolations.size()).isEqualTo(1); 
		ConstraintViolation<Disease> violation =   constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("severity"); 
		assertThat(violation.getMessage()).isEqualTo("must match "+s) ; 
		
	}
	//Update positive
	@Test
	@Transactional
	void shouldUpdateDisease() {
		Disease disease = this.diseaseService.findDiseaseById(1);
		String oldSymptoms = disease.getSymptoms();
		String newSymptoms = oldSymptoms + "XXXXXXXX";

		disease.setSymptoms(newSymptoms);
		this.diseaseService.saveDisease(disease);
		disease = this.diseaseService.findDiseaseById(1);
		assertThat(disease.getSymptoms()).isEqualTo(newSymptoms);
		
	}

	@ParameterizedTest
	@ValueSource(ints = {1})
	void shoulThrowExceptionUpdateDisease(int id){
		Disease disease = this.diseaseService.findDiseaseById(id);
		String newSymptoms = null;
		disease.setSymptoms(newSymptoms);
		this.diseaseService.saveDisease(disease);
		Assertions.assertThrows(NullPointerException.class, () -> {this.diseaseService.findDiseaseById(id).getSymptoms().isEmpty();});
		
	}

	@Test
	void shouldDeletetDisease() {
		Collection<Disease> found1 = this.diseaseService.findAll();
		int count1 = found1.size();
		Disease disease1 = this.diseaseService.findDiseaseById(1);
		this.diseaseService.delete(disease1);
		Collection<Disease> found2 = this.diseaseService.findAll();
		int count2 = found2.size();
		assertTrue(count2 < count1);


	}

	@ParameterizedTest
	@ValueSource(ints = {-16, -30})
	void shouldThrowExceptionDeleteDisease(int id){
		Disease disease1 = this.diseaseService.findDiseaseById(id);
		Assertions.assertThrows(InvalidDataAccessApiUsageException.class, () -> {this.diseaseService.delete(disease1);});
	}
}
