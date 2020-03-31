package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Opinion;

/**
 * OpinionService
 */
@DataJpaTest(includeFilters = @ComponentScan.Filter(Opinion.class))
public class OpinionServiceTest {

    @Autowired
    private OpinionService opinionService;

    
}