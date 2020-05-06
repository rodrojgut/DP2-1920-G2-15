
package org.springframework.samples.petclinic.web.endToEnd;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class OpinionControllerE2ETest {

	private static final int	TEST_OPINION_ID	= 1;

	private static final String	TEST_USER_ID	= "owner1";

	private static final int	TEST_VET_ID		= 1;

	@Autowired
	private MockMvc				mockMvc;


	//  Test del Create

	@WithMockUser(username = "owner1", authorities = {
		"owner"
	})
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/opinions/new/{vetId}", OpinionControllerE2ETest.TEST_VET_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("opinion"))
			.andExpect(MockMvcResultMatchers.view().name("opinions/createOrUpdateOpinions"));
	}

	@WithMockUser(username = "owner1", authorities = {
		"owner"
	})
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/opinions/new/{vetId}", OpinionControllerE2ETest.TEST_VET_ID).param("puntuation", "4").param("comentary", "Successful test").with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/vets/"));
	}

	@WithMockUser(username = "owner1", authorities = {
		"owner"
	})
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/opinions/new/{vetId}", OpinionControllerE2ETest.TEST_VET_ID)				// Tanto date como user se asignan en el controller por lo que no se pueden asignar incorrectamente en el formulario.
			.with(SecurityMockMvcRequestPostProcessors.csrf())).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("opinion"))	// Comentary es un atributo que puede ir vacío.
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("opinion", "puntuation")).andExpect(MockMvcResultMatchers.view().name("opinions/createOrUpdateOpinions"));
	}

	//	Test del List

	@WithMockUser(username = "owner1", authorities = {
		"owner"
	})
	@Test
	void testInitListForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/opinions/list")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("opinions"))
			.andExpect(MockMvcResultMatchers.view().name("/opinions/listOpinions"));
	}

	@WithMockUser(username = "owner1", authorities = {
		"owner"
	})
	@Test
	void testProcessListFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/opinions/list")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("/opinions/listOpinions"));
	}

	@WithMockUser(username = "owner1", authorities = {
		"owner"
	})
	@Test
	void testProcessListFormMine() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/opinions/listMine")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("/opinions/listOpinions"));
	}

	@WithMockUser(username = "vet1", authorities = {
		"veterinarian"
	})
	@Test
	void testProcessListFormNoFound() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/opinions/list")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "vet1", authorities = {
		"veterinarian"
	})
	@Test
	void testProcessListMineFormNoFound() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/opinions/listMine")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	//  Test del Update

	@WithMockUser(username = "owner1", authorities = {
		"owner"
	})
	@Test
	void testInitEditOpinionForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/opinions/edit/{opinionId}", OpinionControllerE2ETest.TEST_OPINION_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("opinion"))
			.andExpect(MockMvcResultMatchers.model().attribute("opinion", Matchers.hasProperty("user"))).andExpect(MockMvcResultMatchers.model().attribute("opinion", Matchers.hasProperty("puntuation", Matchers.is(5))))
			.andExpect(MockMvcResultMatchers.model().attribute("opinion", Matchers.hasProperty("comentary", Matchers.is("Muy buen servicio")))).andExpect(MockMvcResultMatchers.model().attribute("opinion", Matchers.hasProperty("date")))
			.andExpect(MockMvcResultMatchers.model().attribute("opinion", Matchers.hasProperty("vet"))).andExpect(MockMvcResultMatchers.view().name("opinions/createOrUpdateOpinions"));
	}

	@WithMockUser(username = "owner1", authorities = {
		"owner"
	})
	@Test
	void testProcessEditOpinionFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/opinions/edit/{opinionId}", OpinionControllerE2ETest.TEST_OPINION_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("comentary", "Pues no está tan bien.").param("puntuation", "5"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/opinions/listMine"));
	}

	@WithMockUser(username = "owner1", authorities = {
		"owner"
	})
	@Test
	void testProcessEditOpinionFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/opinions/edit/{opinionId}", OpinionControllerE2ETest.TEST_OPINION_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("comentary", "Pues no está tan bien."))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("opinion")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("opinion", "puntuation"))
			.andExpect(MockMvcResultMatchers.view().name("opinions/createOrUpdateOpinions"));
	}

	// Test del delete

	@WithMockUser(username = "owner1", authorities = {
		"owner"
	})
	@Test
	void testProcessDeleteOpinionSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/opinions/{opinionId}/delete", OpinionControllerE2ETest.TEST_OPINION_ID)).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/opinions/listMine"));
	}

	@WithMockUser(username = "owner1", authorities = {
		"owner"
	})
	@Test
	void testProcessDeleteOpinionFail() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/opinions/{opinionId}/delete", 2)).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/opinions/listMine"));
	}

}
