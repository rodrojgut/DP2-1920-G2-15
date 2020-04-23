
package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.service.BookingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookingController {

	private final BookingService	bookingService;

	private static final String		VIEWS_CHIPS_CREATE_OR_UPDATE_FORM	= "";


	@Autowired
	public BookingController(final BookingService bookingService) {
		this.bookingService = bookingService;

	}

	@GetMapping(value = "/bookings/list")
	public String listOpinion(final ModelMap modelMap) {
		Iterable<Booking> bookings = this.bookingService.findAll();
		modelMap.addAttribute("bookings", bookings);
		return "bookings/listBookings";
	}
}
