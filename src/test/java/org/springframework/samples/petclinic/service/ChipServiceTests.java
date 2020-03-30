package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Chip;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class ChipServiceTests {        
    
    @Autowired
    protected ChipService chipService;


    @Test
	void shouldFindChipWithCorrectId() {
		final Chip chip2 = this.chipService.findChipById(2);
		assertThat(chip2.getSerialNumber()).isEqualTo("2");
		assertThat(chip2.getModel()).isEqualTo("model2");
    }
}