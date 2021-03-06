package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.hamcrest.Matchers.hasProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Disease;

import org.springframework.samples.petclinic.model.Pet;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.is;

import static org.mockito.BDDMockito.given;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import org.springframework.samples.petclinic.service.DiseaseService;

import org.springframework.samples.petclinic.service.PetService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;




@WebMvcTest(controllers=DiseaseController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),excludeAutoConfiguration= SecurityConfiguration.class)
public class DiseaseControllerTests {

	private static final int TEST_DISEASE_ID = 1;
	private static final int TEST_DISEASE_ID_NEGATIVE = -100;
	private static final int TEST_PET_ID = 1;
	
	
	
	@MockBean
	private DiseaseService	diseaseService;

	@MockBean
	private PetService petService;

	
	@Mock 
	private Pet pet;
	
	private Disease disease;
	
	@Autowired
	private MockMvc mockMvc;
	
	

	
	@BeforeEach	
	void setup() {

		disease = new Disease();
		pet = new Pet();
		disease.setId(TEST_DISEASE_ID);
		disease.setPet(pet);
		pet.setId(1);
		disease.setCure("Hay que recetarle unas pastillas..");
		disease.setSeverity("LOW");
		disease.setSymptoms("Se encuentra mareado y con diarrea");
		List<Disease> diseases = new ArrayList<>();
		diseases.add(disease);
		given(this.diseaseService.findDiseaseById(TEST_DISEASE_ID)).willReturn(disease);
		given(this.diseaseService.findDiseaseById(TEST_DISEASE_ID_NEGATIVE)).willReturn(null);
		given(this.petService.findPetById(TEST_PET_ID)).willReturn(pet);

	}

	
	@WithMockUser(value = "spring")
    @Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/diseases/new/{petId}",TEST_PET_ID)).andExpect(status().isOk()).andExpect(model().attributeExists("disease"))
				.andExpect(view().name("diseases/createOrUpdateDiseaseForm"));
	}

	@WithMockUser(value = "spring")
        @Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/diseases/new/{petId}",TEST_PET_ID)
					.requestAttr("pet", 1)
					.with(csrf())
							.param("symptoms", "Se encuentra mareado y con taquicardias")
							.param("cure", "Hay que recetarle unas pastillas nuevas para el corazÃ³n..")
							.param("severity", "MEDIUM"))
				.andExpect(status().is3xxRedirection());
	}


	//Negative
	@WithMockUser(value = "spring")
    @Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/diseases/new/{petId}",TEST_PET_ID)
							.with(csrf())
							.param("cure", "Lo estamos intentando")
							.param("severity", "LOW"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("disease"))
				.andExpect(model().attributeHasFieldErrors("disease", "symptoms"))
				.andExpect(view().name("diseases/createOrUpdateDiseaseForm"));
	}


    @WithMockUser(value = "spring")
	@Test
	void testInitUpdateDiseaseForm() throws Exception {
		mockMvc.perform(get("/diseases/{diseaseId}/edit", TEST_DISEASE_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("disease"))
				.andExpect(model().attribute("disease", hasProperty("cure", is("Hay que recetarle unas pastillas.."))))
				.andExpect(model().attribute("disease", hasProperty("severity", is("LOW"))))
				.andExpect(model().attribute("disease", hasProperty("symptoms", is("Se encuentra mareado y con diarrea"))))
				.andExpect(model().attribute("disease", hasProperty("pet")))
				.andExpect(view().name("diseases/createOrUpdateDiseaseForm"));
	}
        
        

    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateDiseaseFormSuccess() throws Exception {
		mockMvc.perform(post("/diseases/{diseaseId}/edit", TEST_DISEASE_ID)
							.with(csrf())
							.param("cure", "Hemos cambiado su cura...")
							.param("severity", "HIGH")
							.param("symptoms", "Se siente peor que antes.."))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/diseases/{diseaseId}"));
	}

	//Negative
    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateDiseaseFormHasErrors() throws Exception {
		mockMvc.perform(post("/diseases/{diseaseId}/edit", TEST_DISEASE_ID)
							.with(csrf())
							.param("cure", "No comida basura")
							.param("severity", "BAJO")
							.param("symptoms", "Se siente peor que antes.."))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("disease"))
				.andExpect(model().attributeHasFieldErrors("disease", "severity"))
				.andExpect(view().name("diseases/createOrUpdateDiseaseForm"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testShowDisease() throws Exception {
		mockMvc.perform(get("/diseases/{diseaseId}", TEST_DISEASE_ID)).andExpect(status().isOk())
				.andExpect(model().attribute("disease", hasProperty("cure", is("Hay que recetarle unas pastillas.."))))
				.andExpect(model().attribute("disease", hasProperty("severity", is("LOW"))))
				.andExpect(model().attribute("disease", hasProperty("symptoms", is("Se encuentra mareado y con diarrea"))))
				.andExpect(model().attribute("disease", hasProperty("pet")))
				.andExpect(view().name("diseases/diseaseDetails"));
	}
    @WithMockUser(value = "spring")
   	@Test
   	void testShowDiseaseError() throws Exception {
   		mockMvc.perform(get("/diseases/{diseaseId}",TEST_DISEASE_ID_NEGATIVE)).
   		andExpect(status().is2xxSuccessful())
		.andExpect(view().name("exception"));
   	}
    
   
    
    @WithMockUser(value = "spring")
  	@Test
  	void testListDisease() throws Exception {
  		mockMvc.perform(get("/diseases/diseasesList")).andExpect(status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("diseases"));
	  }
    


	@WithMockUser(value = "spring")
	@Test
	void testDelete() throws Exception{
	  this.mockMvc.perform(MockMvcRequestBuilders
	  .get("/diseases/delete/{diseaseId}",TEST_DISEASE_ID).queryParam("id", "1"))
	  .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
	  .andExpect(MockMvcResultMatchers.view().name("redirect:/diseases/diseasesList"));
  
	  }
	//DELETE NEGATIVE
	@WithMockUser(username = "spring", authorities = {"veterinarian"})
	@Test
	void testDeleteIncorrectId() throws Exception {
		mockMvc.perform(get("/diseases/delete/{diseaseId}",TEST_DISEASE_ID_NEGATIVE))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/oups"));
	}

}
