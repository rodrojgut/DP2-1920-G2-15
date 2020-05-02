
package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Room;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.RoomRepository;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
class BookingServiceTest {

	@Autowired
	protected BookingService	bookingService;

	@Autowired
	protected PetRepository		petRepo;

	@Autowired
	protected RoomRepository	roomRepo;

	@Autowired
	protected VetRepository		vetRepo;

	@Autowired
	protected OwnerRepository	ownerRepo;

	private Booking				bookingTest;


	// Tests positivos
	@Test
	void shouldSaveTest() throws IllegalArgumentException {

		Iterable<Booking> aux = this.bookingService.findAll();
		Collection<Booking> bookings = new HashSet<>();
		aux.iterator().forEachRemaining(x -> bookings.add(x));
		int found = bookings.size();

		Vet vetTest = this.vetRepo.findById(1);
		Room roomTest = this.roomRepo.findById(1);
		Pet petTest = this.petRepo.findById(1);

		this.bookingTest = new Booking();
		this.bookingTest.setDate(LocalDate.of(2020, 04, 12));
		this.bookingTest.setOwner(petTest.getOwner());
		this.bookingTest.setPet(petTest);
		this.bookingTest.setRoom(roomTest);
		this.bookingTest.setVet(vetTest);

		this.bookingService.save(this.bookingTest);
		Assertions.assertThat(this.bookingTest.getId().longValue()).isNotEqualTo(0);

		aux = this.bookingService.findAll();
		aux.iterator().forEachRemaining(x -> bookings.add(x));
		Assertions.assertThat(bookings.size()).isEqualTo(found + 1);
	}

	@Test
	void findIdTest() {
		Optional<Booking> bookingOpt = this.bookingService.findById(1);
		Assert.assertTrue(bookingOpt.isPresent());
		Assertions.assertThat(bookingOpt.get().getId()).isEqualTo(1);
		Assertions.assertThat(bookingOpt.get().getDate()).isEqualTo(LocalDate.of(2020, 01, 04));
		Assertions.assertThat(bookingOpt.get().getPet()).isEqualTo(this.petRepo.findById(1));
		Assertions.assertThat(bookingOpt.get().getVet()).isEqualTo(this.vetRepo.findById(1));
		Assertions.assertThat(bookingOpt.get().getOwner()).isEqualTo(this.ownerRepo.findById(1));
		Assertions.assertThat(bookingOpt.get().getRoom()).isEqualTo(this.roomRepo.findById(1));
	}

	@Test
	void findAllTest() {
		Iterable<Booking> list = this.bookingService.findAll();
		Assert.assertNotNull(list.iterator().next());
	}

	@Test
	void deleteTest() {
		Booking aux = this.bookingService.findById(1).get();
		this.bookingService.deleteBooking(aux);
		Assertions.assertThat(this.bookingService.findById(1).isPresent()).isFalse();
	}

	// Test negativos
	void shouldNotSaveTest() {
		Booking booking = new Booking();

		try {
			this.bookingService.save(booking);
		} catch (Exception e) {
			Assertions.assertThat(e).isNotNull();
		}
	}

	void shouldNotDeleteTest() {
		Booking booking = new Booking();

		try {
			this.bookingService.deleteBooking(booking);
		} catch (Exception e) {
			Assertions.assertThat(e).isNotNull();
		}
	}
}
