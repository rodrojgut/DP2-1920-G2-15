package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

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
	void shouldDeleteChip() {
        final Chip chip2 = this.chipService.findChipById(2);
        this.chipService.deleteChip(chip2);
        final Chip deleted = this.chipService.findChipById(2);
		assertThat(deleted).isEqualTo(null);

	}
}