package org.springframework.samples.petclinic.web;

import java.util.DuplicateFormatFlagsException;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Room;
import org.springframework.samples.petclinic.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RoomController {

	private final RoomService roomService;

	@Autowired
	public RoomController(RoomService roomService/* , final BookingService bookingService */) {
		this.roomService = roomService;
		// this.bookingService = bookingService;
	}

	@GetMapping("/rooms/roomsList")
	public String findRoom(final ModelMap modelMap) {

		Iterable<Room> rooms = this.roomService.findAll();
		modelMap.addAttribute("rooms", rooms);
		return "rooms/roomsList";
	}

	@GetMapping("/rooms/{roomId}")
	public ModelAndView showRoom(@PathVariable("roomId") final int roomId) {
		final ModelAndView mav = new ModelAndView("rooms/roomDetails");
		mav.addObject(this.roomService.findRoomById(roomId));
		return mav;
	}

}