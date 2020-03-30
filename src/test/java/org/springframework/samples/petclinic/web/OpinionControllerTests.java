
package org.springframework.samples.petclinic.web;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Opinion;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.OpinionService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * OpinionControllerTests
 */
@WebMvcTest(controllers = OpinionController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class OpinionControllerTests {

	private static final int	TEST_OPINION_ID	= 1;

	private static final int	TEST_VET_ID		= 1;

	@Autowired
	private OpinionController	opinionController;

	@MockBean
	private OpinionService		opinionService;

	@MockBean
	private UserService			userService;

	@MockBean
	private AuthoritiesService	authoritiesService;

	@Autowired
	private MockMvc				mockMvc;

	private Opinion				opinion;


	@BeforeEach
	void setup() {
		User userTest = new User();
		Vet vetTest = new Vet();
		userTest.setUsername("owner1");
		vetTest.setId(OpinionControllerTests.TEST_VET_ID);
		this.opinion = new Opinion();
		this.opinion.setId(OpinionControllerTests.TEST_OPINION_ID);
		this.opinion.setComentary("Muy buen servicio");
		this.opinion.setPuntuation(5);
		this.opinion.setUser(userTest);
		this.opinion.setVet(vetTest);
		this.opinion.setDate(LocalDateTime.of(2020, 01, 04, 00, 00, 00));
		BDDMockito.given(this.opinionService.findOpinionById(OpinionControllerTests.TEST_OPINION_ID).get()).willReturn(this.opinion);

	}

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/opinions/new/{vetId}", OpinionControllerTests.TEST_VET_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("opinion"))
			.andExpect(MockMvcResultMatchers.view().name("opinions/createOrUpdateOwnerForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/opinions/new/{vetId}", OpinionControllerTests.TEST_VET_ID).param("puntuation", "4").param("comentary", "Successful test").param("date", "2020/04/23 17:50").param("user", "userTest"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/opinions/new/{vetId}", OpinionControllerTests.TEST_VET_ID).param("date", "2020/04/23 17:50")				// Tanto date como user se asignan en el controller por lo que no se pueden asignar incorrectamente en el formulario.
			.param("user", "userTest")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("opinion")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("opinion", "puntuation"))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("opinion", "comentary")).andExpect(MockMvcResultMatchers.view().name("opinions/createOrUpdateOwnerForm"));
	}

}
