
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
		this.owner.setId(1);
		this.owner.addPet(this.petTest);
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
		this.mockMvc.perform(MockMvcRequestBuilders.post("/bookings/new").param("date", "2020-01-04").param("pet", "1").param("vet", "1").param("room", "1").with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/bookings/list"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/opinions/new/{vetId}", BookingControllerTests.TEST_VET_ID).param("date", "2020/04/23 17:50")				// Tanto date como user se asignan en el controller por lo que no se pueden asignar incorrectamente en el formulario.
			.param("user", "userTest").with(SecurityMockMvcRequestPostProcessors.csrf())).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("opinion"))	// Comentary es un atributo que puede ir vacío.
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("opinion", "puntuation")).andExpect(MockMvcResultMatchers.view().name("opinions/createOrUpdateOpinions"));
	}

	//	Test del List

	@WithMockUser(value = "spring")
	@Test
	void testInitListForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/opinions/list")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("opinions"))
			.andExpect(MockMvcResultMatchers.view().name("/opinions/listOpinions"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessListFormSuccess() throws Exception {
		List<Opinion> itList = new ArrayList<>();
		itList.add(this.opinion.get());
		Iterable<Opinion> it = itList;
		BDDMockito.given(this.opinionService.findAll()).willReturn(it);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/opinions/list")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("/opinions/listOpinions"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessListFormMine() throws Exception {
		List<Opinion> itList = new ArrayList<>();
		itList.add(this.opinion.get());
		Iterable<Opinion> it = itList;
		BDDMockito.given(this.opinionService.findAllMine(BookingControllerTests.TEST_USER_ID)).willReturn(it);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/opinions/listMine")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("/opinions/listOpinions"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessListFormNoFound() throws Exception {
		List<Opinion> itList = new ArrayList<>();
		Iterable<Opinion> it = itList;
		this.mockMvc.perform(MockMvcRequestBuilders.get("/opinions/list")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attribute("opinions", it))
			.andExpect(MockMvcResultMatchers.view().name("/opinions/listOpinions"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessListMineFormNoFound() throws Exception {
		List<Opinion> itList = new ArrayList<>();
		Iterable<Opinion> it = itList;
		this.mockMvc.perform(MockMvcRequestBuilders.get("/opinions/listMine")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attribute("opinions", it))
			.andExpect(MockMvcResultMatchers.view().name("/opinions/listOpinions"));
	}

	//  Test del Update

	@WithMockUser(value = "owner1")
	@Test
	void testInitEditOpinionForm() throws Exception {
		User userMock = new User();
		userMock.setUsername(BookingControllerTests.TEST_USER_ID);
		userMock.setEnabled(false);
		userMock.setPassword(null);
		this.vetMock.setId(BookingControllerTests.TEST_VET_ID);
		this.mockMvc.perform(MockMvcRequestBuilders.get("/opinions/edit/{opinionId}", BookingControllerTests.TEST_OPINION_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("opinion"))
			.andExpect(MockMvcResultMatchers.model().attribute("opinion", Matchers.hasProperty("user", Matchers.is(userMock)))).andExpect(MockMvcResultMatchers.model().attribute("opinion", Matchers.hasProperty("puntuation", Matchers.is(5))))
			.andExpect(MockMvcResultMatchers.model().attribute("opinion", Matchers.hasProperty("comentary", Matchers.is("Muy buen servicio"))))
			.andExpect(MockMvcResultMatchers.model().attribute("opinion", Matchers.hasProperty("date", Matchers.is(LocalDateTime.of(2020, 01, 04, 00, 00, 00)))))
			.andExpect(MockMvcResultMatchers.model().attribute("opinion", Matchers.hasProperty("vet", Matchers.is(this.vetMock)))).andExpect(MockMvcResultMatchers.view().name("opinions/createOrUpdateOpinions"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessEditOpinionFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/opinions/edit/{opinionId}", BookingControllerTests.TEST_OPINION_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("comentary", "Pues no está tan bien.").param("puntuation", "5"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/opinions/listMine"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessEditOpinionFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/opinions/edit/{opinionId}", BookingControllerTests.TEST_OPINION_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("comentary", "Pues no está tan bien."))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("opinion")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("opinion", "puntuation"))
			.andExpect(MockMvcResultMatchers.view().name("opinions/createOrUpdateOpinions"));
	}

	// Test del delete

	@WithMockUser(value = "spring")
	@Test
	void testProcessDeleteOpinionSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/opinions/{opinionId}/delete", BookingControllerTests.TEST_OPINION_ID)).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/opinions/listMine"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessDeleteOpinionFail() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/opinions/{opinionId}/delete", 2)).andExpect(MockMvcResultMatchers.model().attribute("message", "Opinion not found.")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
			.andExpect(MockMvcResultMatchers.view().name("/exception"));
	}
}
