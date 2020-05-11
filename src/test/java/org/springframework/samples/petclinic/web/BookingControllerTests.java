
package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Opinion;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Room;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.BookingService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.RoomService;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * OpinionControllerTests
 */
@WebMvcTest(controllers = BookingController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class BookingControllerTests {

	private static final int	TEST_BOOKING_ID	= 1;

	private static final int	TEST_ROOM_ID	= 1;

	private static final int	TEST_VET_ID		= 1;

	private static final int	TEST_PET_ID		= 1;

	@Autowired
	private BookingController	bookingController;

	@MockBean
	private BookingService		bookingService;

	@MockBean
	private VetService			vetService;

	@MockBean
	private RoomService			roomService;

	@MockBean
	private PetService			petService;

	@MockBean
	private AuthoritiesService	authoritiesService;

	@Autowired
	private MockMvc				mockMvc;

	private Optional<Booking>	booking;

	private Vet					vetTest;

	private Room				roomTest;

	@Mock()
	private Pet					petTest;

	@Mock()
	private Owner				owner;


	@BeforeEach
	void setup() {
		petTest = new Pet();
		petTest.setId(TEST_PET_ID);
		this.owner = new Owner();
		this.owner.setId(1);
		this.owner.addPet(this.petTest);
		vetTest = new Vet();
		vetTest.setId(TEST_VET_ID);
		roomTest = new Room();
		roomTest.setId(TEST_ROOM_ID);
		Booking book = new Booking();
		book.setId(BookingControllerTests.TEST_BOOKING_ID);
		book.setVet(this.vetTest);
		book.setOwner(this.petTest.getOwner());
		book.setPet(this.petTest);
		book.setRoom(this.roomTest);
		book.setDate(LocalDate.of(2020, 01, 04));
		this.booking = Optional.of(book);
		BDDMockito.given(this.vetService.findById(BookingControllerTests.TEST_VET_ID)).willReturn(this.vetTest);
		BDDMockito.given(this.petService.findPetById(BookingControllerTests.TEST_PET_ID)).willReturn(this.petTest);
		BDDMockito.given(this.roomService.findRoomById(BookingControllerTests.TEST_ROOM_ID)).willReturn(this.roomTest);
		BDDMockito.given(this.bookingService.findById(BookingControllerTests.TEST_BOOKING_ID)).willReturn(this.booking);

	}

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
		List<Booking> itList = new ArrayList<>();
		itList.add(this.booking.get());
		Iterable<Booking> it = itList;
		BDDMockito.given(this.bookingService.findAll()).willReturn(it);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/bookings/list")).andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name("bookings/listBookings"));
	}


	@WithMockUser(value = "spring" , authorities = "veterinarian")
	@Test
	void testProcessListFormNoFound() throws Exception {
		List<Booking> itList = new ArrayList<>();
		Iterable<Booking> it = itList;
		this.mockMvc.perform(MockMvcRequestBuilders.get("/bookings/list")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attribute("bookings", it))
			.andExpect(MockMvcResultMatchers.view().name("bookings/listBookings"));
	}

	//  Test del Update

	@WithMockUser(value = "spring", authorities = "veterinarian")
	@Test
	void testInitEditBookingForm() throws Exception {
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/bookings/edit/{idBooking}", BookingControllerTests.TEST_BOOKING_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("booking"))
			.andExpect(MockMvcResultMatchers.model().attribute("booking", Matchers.hasProperty("vet", Matchers.is(vetTest))))
			.andExpect(MockMvcResultMatchers.model().attribute("booking", Matchers.hasProperty("date", Matchers.is(LocalDate.of(2020, 01, 04)))))
			.andExpect(MockMvcResultMatchers.model().attribute("booking", Matchers.hasProperty("owner", Matchers.is(this.owner))))
			.andExpect(MockMvcResultMatchers.model().attribute("booking", Matchers.hasProperty("pet", Matchers.is(this.petTest))))
			.andExpect(MockMvcResultMatchers.model().attribute("booking", Matchers.hasProperty("room", Matchers.is(this.roomTest))))
			.andExpect(MockMvcResultMatchers.model().attribute("oldPetId",Matchers.is(TEST_PET_ID)))
			.andExpect(MockMvcResultMatchers.model().attribute("oldRoomId",Matchers.is(TEST_ROOM_ID)))
			.andExpect(MockMvcResultMatchers.model().attribute("oldVetId",Matchers.is(TEST_VET_ID)))
			.andExpect(MockMvcResultMatchers.view().name("bookings/createOrUpdateBookingForm"));
	}

	@WithMockUser(value = "spring", authorities = "veterinarian")
	@Test
	void testProcessEdiBookingFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/bookings/edit/{idBooking}", BookingControllerTests.TEST_BOOKING_ID).with(SecurityMockMvcRequestPostProcessors.csrf())
		.param("fecha", "2020-01-04")
		.param("petId", "1")
		.param("vetId", "1")
		.param("roomId", "1"))
		.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/bookings/list"));
	}

	// Test del delete

	@WithMockUser(value = "spring" , authorities = "veterinarian")
	@Test
	void testProcessDeleteOpinionSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/bookings/{bookingId}/delete", BookingControllerTests.TEST_BOOKING_ID)).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/bookings/list"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessDeleteOpinionFail() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/bookings/{bookingId}/delete", 2)).andExpect(MockMvcResultMatchers.model().attribute("message", "Booking not found.")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
			.andExpect(MockMvcResultMatchers.view().name("/exception"));
	}
}
