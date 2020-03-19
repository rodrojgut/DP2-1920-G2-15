
package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.service.ChipService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/owners/{ownerId}/pets/{petId}")
public class ChipController {

	private ChipService	chipService;
	
	@Autowired
	public ChipController(ChipService chipService) {
		this.chipService = chipService;
	}
	
	@GetMapping("/chips/{chipId}")
	public ModelAndView showChip(@PathVariable("chipId") int chipId) {
		ModelAndView mav = new ModelAndView("chips/chipDetails");
		mav.addObject(chipService.findChipById(chipId));
		return mav;
	}
	
}
