package org.springframework.samples.petclinic.web;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.service.BookingService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bookings")
public class BookingController {

	private final BookingService bookingService;
	
	private final VetService vetService;
	
	private final OwnerService ownerService;

	private final PetService petService;
	
	private static final String	VIEWS_BOOKING_CREATE_OR_UPDATE_FORM	= "bookings/createOrUpdateBookingForm";


	@Autowired
	public BookingController( BookingService bookingService,VetService vetService,
			 OwnerService ownerService, PetService petService ) {
		this.bookingService = bookingService;
		this.vetService = vetService;
		this.ownerService = ownerService;
		this.petService = petService;

	
	}

	@GetMapping(value = "/new")
	public String createInitForm(ModelMap model){
		 Booking booking = new Booking();
		 model.put("booking", booking);
		 
		 Collection<Vet> vets = this.vetService.findVets();
		 model.put("vets",vets);

		 Collection<Owner> owners = this.ownerService.findAllOwners();
		 model.put("owners", owners);

		 Collection<Pet> pets = this.petService.findAllPets();
		 model.put("pets", pets);
		 return VIEWS_BOOKING_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/new")
	public String proccessCreationForm(@Valid Booking booking, final BindingResult result, final ModelMap model){
		if (result.hasErrors()) {
			model.put("booking", booking);
			
			//model.put("petId", petId);
			return VIEWS_BOOKING_CREATE_OR_UPDATE_FORM;
		} else {
			System.out.println(model.get("owner"));
			//this.bookingService.saveBooking(booking);
			return "redirect:/bookings/list";
		}
	}

	
}