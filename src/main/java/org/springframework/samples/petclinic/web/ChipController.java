
package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Chip;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.ChipService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
	
	@GetMapping(value = "/chips/{chipId}/delete")
	public String removeChip(@PathVariable("chipId") int chipId, ModelMap model) {
		Chip chip = chipService.findChipById(chipId);
		chipService.deleteChip(chip);
		model.addAttribute("message", "Chip succefully deleted!");
		return "redirect:/owners/{ownerId}";
	}
}
