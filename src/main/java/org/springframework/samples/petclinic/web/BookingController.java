package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.service.BookingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;
	
	private static final String	VIEWS_CHIPS_CREATE_OR_UPDATE_FORM	= "";


	@Autowired
	public BookingController( BookingService bookingService) {
		this.bookingService = bookingService;
	
	}

}