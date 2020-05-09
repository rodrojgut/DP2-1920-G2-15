package org.springframework.samples.petclinic.web.endToEnd;

import javax.transaction.Transactional;

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

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class BookingControllerE2ETest {

	private static final int	TEST_BOOKING_ID	= 1;

	@Autowired
	private MockMvc				mockMvc;



	//  Test del Create

	@WithMockUser(value = "spring", authorities = "veterinarian")
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/bookings/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("booking"))
			.andExpect(MockMvcResultMatchers.view().name("bookings/createOrUpdateBookingForm"));
	}

	@WithMockUser(value = "spring", authorities = "veterinarian")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/bookings/new").param("fecha", "2020-01-04").param("petId", "1").param("vetId", "1").param("roomId", "1").with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/bookings/list"));
	}

	//	Test del List

	@WithMockUser(value = "spring", authorities = "veterinarian")
	@Test
	void testInitListForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/bookings/list")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("bookings"))
			.andExpect(MockMvcResultMatchers.view().name("bookings/listBookings"));
	}

	@WithMockUser(value = "spring", authorities = "veterinarian")
	@Test
	void testProcessListFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/bookings/list")).andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name("bookings/listBookings"));
	}


	@WithMockUser(value = "spring" )
	@Test
	void testProcessListFormNoFound() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/bookings/list")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	//  Test del Update

	@WithMockUser(value = "spring", authorities = "veterinarian")
	@Test
	void testInitEditBookingForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/bookings/edit/{idBooking}",TEST_BOOKING_ID))
		.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model()
		.attributeExists("booking","oldPetId","oldRoomId","oldVetId"))
		.andExpect(MockMvcResultMatchers.view().name("bookings/createOrUpdateBookingForm"));
	}

	@WithMockUser(value = "spring", authorities = "veterinarian")
	@Test
	void testProcessEdiBookingFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/bookings/edit/{idBooking}", TEST_BOOKING_ID).with(SecurityMockMvcRequestPostProcessors.csrf())
		.param("fecha", "2020-02-04")
		.param("petId", "2")
		.param("vetId", "2")
		.param("roomId", "2"))
		.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/bookings/list"));
	}

	// Test del delete

	@WithMockUser(value = "spring" , authorities = "veterinarian")
	@Test
	void testProcessDeleteOpinionSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/bookings/{bookingId}/delete", TEST_BOOKING_ID)).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/bookings/list"));
	}

	@WithMockUser(value = "spring", authorities = "veterinarian")
	@Test
	void testProcessDeleteOpinionFail() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/bookings/{bookingId}/delete",10 )).andExpect(MockMvcResultMatchers.model().attribute("message", "Booking not found.")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
			.andExpect(MockMvcResultMatchers.view().name("/exception"));
	}
}
