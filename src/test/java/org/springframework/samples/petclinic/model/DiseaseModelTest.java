package org.springframework.samples.petclinic.model;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.xml.validation.Validator;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class DiseaseModelTest extends ValidatorTests {
	
	@Test
	void testGettandSetDisease() {
		Disease disease = new Disease();
		Pet pet = new Pet();
		pet.setId(1);
		disease.setSeverity("LOW");
		disease.setCure("la cura");
		disease.setSymptoms("esta malisimo");
		disease.setPet(pet);
		assertEquals(disease.getCure(),"la cura");
		assertEquals(disease.getSeverity(),"LOW");
		assertEquals(disease.getSymptoms(),"esta malisimo");
		assertEquals(disease.getPet().id,1);
		
		
		
	}
/*	@Test
	void testNoEmptyDisease() {
		
			Disease disease = new Disease();
		 	Validator validator;
		 	

	        // Validate the object
	        Set<ConstraintViolation<Disease>> constraintViolations = validator.(disease.getCure());

	        // This is the line that will cause your unit test to fail if there are not any violations
	        Assert.assertEquals(1, constraintViolations.size());
		
	}
*/
}
