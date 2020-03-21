package org.springframework.samples.petclinic.web;

import java.lang.ProcessBuilder.Redirect;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.samples.petclinic.model.Opinion;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.OpinionService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
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

    private static final String VIEWS_OPINION_CREATE_OR_UPDATE_FORM = "opinions/createOrUpdateOpinions";

    private final OpinionService opinionService;

    public OpinionController(OpinionService opinionService, UserService userService,
            AuthoritiesService authoritiesService) {
        this.opinionService = opinionService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping(value = "/opinions/new/{vetId}")
    public String initCreationForm(Map<String, Object> model, @PathVariable("vetId") Integer vetId) {
        Opinion opinion = new Opinion();
        model.put("opinion", opinion);
        model.put("vetId", vetId);
        return VIEWS_OPINION_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping(value = "/opinions/new/{vetId}")
    public String processCreationForm(@Valid Opinion opinion, BindingResult result,
            @PathVariable("vetId") Integer vetId) {
        if (result.hasErrors()) {
            return VIEWS_OPINION_CREATE_OR_UPDATE_FORM;
        } else {
            // creating owner, user and authorities
            opinion.setDate(LocalDateTime.now());                          //Ponemos la fecha actual
            opinion.setVet(this.opinionService.getVetById(vetId));         //Le asociamos el vet sobre el cual actuamos
            opinion.setUser(this.opinionService.getCurrentUser());       //Obtiene el owner actual para 
			this.opinionService.saveOpinion(opinion);                      //Guardarmos la opinion en el sistema
			
			return "redirect:/vets/" ;
		}
	}

    @GetMapping(value = "/opinions/edit/{opinionId}")
    public String initEditForm(Map<String, Object> model, @PathVariable("opinionId") Integer opinionId){
        Optional<Opinion> o = this.opinionService.findOpinionById(opinionId);
        
        User actualUser = this.opinionService.getCurrentUser();
        if(o.isPresent()&&o.get().getUser().equals(actualUser)){
           model.put("opinion", o.get());
           model.put("user", o.get().getUser());
           return VIEWS_OPINION_CREATE_OR_UPDATE_FORM;
       }else{
           model.put("message", "Opinion not found");
           return "redirect:/opinions/list";
       }
    }

    @PostMapping(value = "/opinions/edit/{opinionId}")
    public String processEditForm(@Valid Opinion opinion ,BindingResult result,
    @PathVariable("opinionId") Integer opinionId, Map<String, Object> model){
        if (result.hasErrors()) {
            return VIEWS_OPINION_CREATE_OR_UPDATE_FORM;
        } else {
            opinion.setId(opinionId);
            
			this.opinionService.saveOpinion(opinion);                      //Guardarmos la opinion en el sistema
			
			return "redirect:/vets/" ;
		}
    }


    
}