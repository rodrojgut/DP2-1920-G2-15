
package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Room;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.service.BookingService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.RoomService;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(value = "/bookings")
@Controller
public class BookingController {

	private final BookingService bookingService;
	
	private final VetService vetService;
	
	private final OwnerService ownerService;

	private final PetService petService;

	private final RoomService roomService;
	
	private static final String	VIEWS_BOOKING_CREATE_OR_UPDATE_FORM	= "bookings/createOrUpdateBookingForm";


	@Autowired
	public BookingController( BookingService bookingService,VetService vetService,
			 OwnerService ownerService, PetService petService, RoomService roomService ) {
		this.bookingService = bookingService;
		this.vetService = vetService;
		this.ownerService = ownerService;
		this.petService = petService;
		this.roomService = roomService;

	
	}


	private void init(ModelMap model){
		Collection<Vet> vets = this.vetService.findVets();
		model.put("vets",vets);

		Collection<Owner> owners = this.ownerService.findAllOwners();
		model.put("owners", owners);

		Collection<Pet> pets = this.petService.findAllPets();
		model.put("pets", pets);

		Collection<Room> rooms = this.roomService.findAll();
		model.put("rooms", rooms);
	}

	@GetMapping(value = "/new")
	public String createInitForm(ModelMap model){
		 Booking booking = new Booking();
		 model.put("booking", booking);
		 init(model); 

		 return VIEWS_BOOKING_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/new")
	public String proccessCreationForm(@Valid Booking booking, final BindingResult result, final ModelMap model,
	HttpServletRequest request, 
	@RequestParam(value = "roomId") Integer roomId,
	@RequestParam(value = "petId") Integer petId,
	@RequestParam(value = "vetId") Integer vetId,
	@RequestParam(value = "fecha") String date){
	
		if (result.hasErrors()) {
			model.put("booking", booking);
			init(model);
			return VIEWS_BOOKING_CREATE_OR_UPDATE_FORM;
		} else {	
			
			Pet pet = this.petService.findPetById(petId);
			Owner owner = pet.getOwner();
			Room room = this.roomService.findRoomById(roomId);
			Vet vet = this.vetService.findById(vetId);
			booking.setDate(LocalDate.parse(date));
			booking.setPet(pet);
			booking.setVet(vet);
			booking.setOwner(owner);
			booking.setRoom(room);;

			this.bookingService.save(booking);
			return "redirect:/bookings/list";
		
		}
	}
	
	@GetMapping(value = "/list")
	public String listOpinion(final ModelMap modelMap) {
		Iterable<Booking> bookings = this.bookingService.findAll();
		modelMap.addAttribute("bookings", bookings);
		return "bookings/listBookings";
	}
}

