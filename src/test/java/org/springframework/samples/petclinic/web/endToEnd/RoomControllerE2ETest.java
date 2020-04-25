package org.springframework.samples.petclinic.web.endToEnd;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class RoomControllerE2ETest {

	private static final int TEST_ROOM_ID_POSITIVE = 1;
	private static final int TEST_ROOM_ID_NEGATIVE = -1;

	@Autowired
	private MockMvc mockMvc;

	@WithMockUser(username = "admin1", authorities = { "admin" })
	@Test
  	void testListRoom() throws Exception {
  		mockMvc.perform(get("/rooms/roomsList")).andExpect(status().isOk()).
  		andExpect(MockMvcResultMatchers.model().attributeExists("rooms"))
  		.andExpect(view().name("rooms/roomsList"));
	  }

	@WithMockUser(username = "vet1", authorities = { "veterinarian" })
	@Test
	void testNotListRoom() throws Exception {
		mockMvc.perform(get("/rooms/roomsList")).andExpect(status().is4xxClientError())
				.andExpect(status().reason("Forbidden"));
	}
	@WithMockUser(username = "admin1", authorities = { "admin" })
	@Test
	void testShowRoom() throws Exception {

		mockMvc.perform(get("/rooms/{roomId}", TEST_ROOM_ID_POSITIVE))
		.andExpect(model().attribute("room", hasProperty("floor", is(1))))
		.andExpect(model().attribute("room", hasProperty("name", is("Quirofano1"))))			
		.andExpect(view().name("rooms/roomDetails"));

		}
	    
	@WithMockUser(username = "admin1", authorities = { "admin" })
	@Test
	void testShowRoomNegative() throws Exception {
			mockMvc.perform(get("/rooms/{roomId}",  TEST_ROOM_ID_NEGATIVE))
			.andExpect(status().is2xxSuccessful())
			.andExpect(view().name("exception"));
		}
	
	    
	@WithMockUser(username = "admin1", authorities = { "admin" })
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/rooms/new")).andExpect(status().isOk())
				.andExpect(model().attributeExists("room"))
				.andExpect(view().name("rooms/createOrUpdateRoomForm"));
	}


	@WithMockUser(username = "vet1", authorities = { "veterinarian" })
	@Test
	void testInitCreationFormFail() throws Exception {
		mockMvc.perform(get("/rooms/new")).andExpect(status().is(403))
				.andExpect(status().reason("Forbidden"));
	}


	@WithMockUser(username = "admin1", authorities = { "admin" })
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/rooms/new")
				.with(csrf())
				.param("floor", "10")
				.param("medicalTeam", "Tijeras")
				.param("name", "Quirofano 5"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/rooms/5"));
	}

	@WithMockUser(username = "vet1", authorities = { "veterinarian" })
	@Test
	void testProcessCreationFormRolError() throws Exception {
		mockMvc.perform(post("/rooms/new"))
				.andExpect(status().is(403))
				.andExpect(status().reason("Forbidden"));
	}

	
	@WithMockUser(username = "admin1", authorities = { "admin" })
	@Test
	void testProcessCreationFormIdError() throws Exception {
		mockMvc.perform(post("/rooms/new")
		.with(csrf())
		.param("floor", "pero si esto es un integer"))
		.andExpect(model().attributeHasErrors("room"))
		.andExpect(model().attributeHasFieldErrors("room", "floor"))
		.andExpect(view().name("rooms/createOrUpdateRoomForm"));
}
	


}