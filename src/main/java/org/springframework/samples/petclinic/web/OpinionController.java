
package org.springframework.samples.petclinic.web;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.samples.petclinic.model.Opinion;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.OpinionService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * OpinionController
 */
@Controller
public class OpinionController {

	private static final String		VIEWS_OPINION_CREATE_OR_UPDATE_FORM	= "opinions/createOrUpdateOpinions";

	private final OpinionService	opinionService;


	public OpinionController(final OpinionService opinionService, final UserService userService, final AuthoritiesService authoritiesService) {
		this.opinionService = opinionService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/opinions/new/{vetId}")
	public String initCreationForm(final Map<String, Object> model, @PathVariable("vetId") final Integer vetId) {
		Opinion opinion = new Opinion();
		model.put("opinion", opinion);
		model.put("vetId", vetId);
		return OpinionController.VIEWS_OPINION_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/opinions/new/{vetId}")
	public String processCreationForm(@Valid final Opinion opinion, final BindingResult result, @PathVariable("vetId") final Integer vetId) {
		if (result.hasErrors()) {
			return OpinionController.VIEWS_OPINION_CREATE_OR_UPDATE_FORM;
		} else {
			// creating owner, user and authorities
			opinion.setDate(LocalDateTime.now());                          //Ponemos la fecha actual
			opinion.setVet(this.opinionService.getVetById(vetId));         //Le asociamos el vet sobre el cual actuamos
			opinion.setUser(this.opinionService.getCurrentUser());       //Obtiene el owner actual para 
			this.opinionService.saveOpinion(opinion);                      //Guardarmos la opinion en el sistema

			return "redirect:/vets/";
		}
	}

	@GetMapping(value = "/opinions/list")
	public String listOpinion(final ModelMap modelMap) {
		Iterable<Opinion> opinions = this.opinionService.findAll();
		List<Opinion> opinionsList = new ArrayList<>();
		opinions.forEach(x -> opinionsList.add(x));		// Introducimos las opinions en un list
		List<Opinion> ordered = opinionsList.stream().sorted(Comparator.comparing(Opinion::getPuntuation).reversed()).collect(Collectors.toList());		// Ordenamos las opinions en orden inverso 
		modelMap.addAttribute("opinions", ordered);
		return "/opinions/listOpinions";
	}

}
