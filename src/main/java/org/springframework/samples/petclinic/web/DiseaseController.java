/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import javax.naming.Binding;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Disease;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.DiseaseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import net.bytebuddy.asm.Advice.Return;
import net.bytebuddy.asm.Advice.This;

import org.springframework.samples.petclinic.service.PetService;

/**
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
@RequestMapping("/diseases")
public class DiseaseController {

	@Autowired
	private DiseaseService DiseaseService;

	@Autowired
	private PetService petService;

	@GetMapping("/diseasesList")
	public Iterable<Disease> findDiseases(final ModelMap modelMap) {

		Iterable<Disease> diseases = this.DiseaseService.findAll();
		modelMap.addAttribute("diseases", diseases);
		return this.DiseaseService.findAll();
	}

	@ModelAttribute("pets")
	public Collection<Pet> populatePet() {
		return this.DiseaseService.findPets();
	}

	/*@GetMapping(value = "/new")
	public String NewDiseases(Map<String, Object> model) {
		Disease disease = new Disease();
		Collection<Pet> pets = diseaseService.findPets();
		model.put("diseases", disease);
		model.put("pets", pets);
		return "diseases/createOrUpdateDiseaseForm";
	}*/

	@GetMapping("/new")
	public String createDisease(final ModelMap modelMap) {
		String view = "diseases/createDisease";
		Disease disease = new Disease();
		modelMap.addAttribute("disease", disease);
		return view;
	}

	@PostMapping("/new")
	public String newDisease(@Valid Disease disease, BindingResult result, ModelMap modelMap){
		String view = "diseases/diseasesList";
		if(result.hasErrors()){
			modelMap.addAttribute("disease", disease);
			return "diseases/createDisease";
		}else{
			Collection<Pet> pet = this.DiseaseService.findPets();
			this.petService.saveAllPets(pet);
			this.DiseaseService.save(disease);
			modelMap.addAttribute("message","Disease sucessfully saved" );
			view = createDisease(modelMap);
		}
		return view;
	}



/*	@GetMapping("/{diseaseId}/edit")
	public String updateDisease(@PathVariable("diseaseId") int diseaseId, ModelMap model) {
		Optional<Disease> disease = this.DiseaseService.findDiseaseById(diseaseId);
		model.addAttribute(disease);
		return "diseases/diseaseDetails";
	}
*/
	@GetMapping("/edit/{diseaseId}")
	public ModelAndView showDisease(@PathVariable("diseaseId") int diseaseId) {
		ModelAndView mav = new ModelAndView("diseases/diseaseDetails");
		mav.addObject(this.DiseaseService.findDiseaseId(diseaseId));
		return mav;
	}
/*
	@PostMapping("/{diseaseId}/edit")
	public String processUpdateDisease(@Valid Disease disease, BindingResult result,
			@PathVariable("diseaseId") int diseaseId) {
		if (result.hasErrors()) {
			return "diseases/createDisease";
		} else {
			disease.setId(diseaseId);
			this.DiseaseService.save(disease);
			return "redirect:/diseases/{diseaseId}";
		}
	}

	/*@GetMapping("/diseases/{diseaseId}")
	public ModelAndView showDisease(@PathVariable("diseaseId") int diseaseId) {
		ModelAndView mav = new ModelAndView("diseases/diseaseDetails");
		mav.addObject(this.DiseaseService.findDiseaseById(diseaseId));
		return mav;
	}*/
}
