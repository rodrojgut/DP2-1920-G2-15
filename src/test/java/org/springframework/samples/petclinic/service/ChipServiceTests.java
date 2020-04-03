package org.springframework.samples.petclinic.service;

import javax.transaction.Transactional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Chip;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class ChipServiceTests {

	@Autowired
	protected ChipService	chipService;

	@Autowired
	protected PetService	petService;


	@Test
	@Transactional
	public void shouldInsertChip() {
		Pet pet = this.petService.findPetById(4);
		Chip chip = new Chip();
		chip.setSerialNumber("4");
		chip.setModel("model4");
		chip.setGeolocatable(false);
		pet.setChip(chip);
		this.chipService.saveChip(chip);
		Assertions.assertThat(chip.getId()).isNotNull();
		Assertions.assertThat(pet.getChip()).isNotNull();
	}
  
  @Test
	@Transactional
	public void shouldThrowExceptionInsertingChipWhitoutModel() {
		Pet pet = this.petService.findPetById(4);
		Chip chip = new Chip();
		chip.setSerialNumber("4");
		chip.setModel("");
		chip.setGeolocatable(false);
		Assertions.assertThrows(Exception.class, () ->{
			pet.setChip(chip);
			this.chipService.saveChip(chip);
		});
	}

	@Test
	@Transactional
	public void shouldUpdateChip() {
		Chip chip = this.chipService.findChipById(1);
		String newModel = "newModel";
		chip.setModel(newModel);
		this.chipService.saveChip(chip);
		chip = this.chipService.findChipById(1);
		Assertions.assertThat(chip.getModel()).isEqualTo(newModel);
	}
  
  @Test
	@Transactional
	public void shouldThrowExceptionUpdatingChipWhitoutModel() {
		Chip chip = this.chipService.findChipById(1);
		chip.setPet(null);
		Assertions.assertThrows(Exception.class, () ->{
			chip.getPet().setChip(chip);
			this.chipService.saveChip(chip);
		});
	}
  
  //Positive
    @Test
	void shouldDeleteChip() {
        final Chip chip2 = this.chipService.findChipById(2);
        this.chipService.deleteChip(chip2);
        final Chip deleted = this.chipService.findChipById(2);
		assertThat(deleted).isEqualTo(null);

    }
    
    //Negative
    @Test
	void shouldNotDeleteChip() {
        boolean pasa = false;
        final Chip chip2 = new Chip();
        try{
            this.chipService.deleteChip(chip2);
        }catch(Exception e){
            pasa = true;
        }
        assertThat(pasa).isTrue();
  }
    @Test
	void shouldFindChipWithCorrectId() {
		final Chip chip2 = this.chipService.findChipById(2);
		assertThat(chip2.getSerialNumber()).isEqualTo("2");
		assertThat(chip2.getModel()).isEqualTo("model2");

    }
}