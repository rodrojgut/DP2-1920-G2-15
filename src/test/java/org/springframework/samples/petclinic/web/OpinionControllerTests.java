package org.springframework.samples.petclinic.web;

import java.time.LocalDateTime;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.BDDMockito.given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Opinion;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.service.OpinionService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;


/**
 * OpinionControllerTests
 */
@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers=OpinionController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)
public class OpinionControllerTests {

    private static final int TEST_OPINION_ID = 1;

    @Autowired
    private OpinionController opinionController;

    @MockBean
    private OpinionService opinionService;

    @Mock
	private User userTest;

	@Mock 
	private Vet vetTest;

    private Opinion opinion;

    @BeforeEach
    void setup(){
        opinion = new Opinion();
        opinion.setId(1);
        opinion.setComentary("Comentario de prueba");
        opinion.setPuntuation(5);
        opinion.setUser(userTest);
        opinion.setVet(vetTest);
        opinion.setDate(LocalDateTime.of(2020, 1, 6, 10, 55, 14));
        given(this.opinionService.findOpinionById(TEST_OPINION_ID).get()).willReturn(opinion);
        
    }
}