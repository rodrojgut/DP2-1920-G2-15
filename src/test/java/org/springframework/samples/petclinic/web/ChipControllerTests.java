package org.springframework.samples.petclinic.web;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Chip;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ChipService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(value = ChipController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
class ChipControllerTests {

    private static final int TEST_OWNER_ID = 1;

    private static final int TEST_PET_ID = 1;

    private static final int TEST_CHIP_ID = 1;

    @Autowired
	private ChipController chipController;

    @MockBean
	private PetService petService;
        
    @MockBean
    private OwnerService ownerService;
    
    @MockBean
	private ChipService chipService;

    @Autowired
    private MockMvc mockMvc;
    

    @BeforeEach
	void setup() {
		
		given(this.ownerService.findOwnerById(TEST_OWNER_ID)).willReturn(new Owner());
        given(this.petService.findPetById(TEST_PET_ID)).willReturn(new Pet());
        given(this.chipService.findChipById(TEST_CHIP_ID)).willReturn(new Chip());
	}

    
    @WithMockUser(value = "spring")
    @Test
	void testInitShowForm() throws Exception {
		mockMvc.perform(get("/owners/{ownerId}/pets/{petId}/chips/{chipId}", TEST_OWNER_ID, TEST_PET_ID, TEST_CHIP_ID)).andExpect(status().isOk());
	}
    
	@WithMockUser(value = "spring")
    @Test
	void testProcessDeleteChipSuccess() throws Exception {
		mockMvc.perform(get("/owners/{ownerId}/pets/{petId}/chips/{chipId}/delete", TEST_OWNER_ID, TEST_PET_ID, TEST_CHIP_ID))
				.andExpect(status().is3xxRedirection());
	}
}