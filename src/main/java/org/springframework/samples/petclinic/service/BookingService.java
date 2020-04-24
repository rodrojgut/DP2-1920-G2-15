
package org.springframework.samples.petclinic.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.repository.BookingRepository;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

	private BookingRepository bookingRepository;


	@Autowired
	public BookingService(final BookingRepository bookingRepository) {
		this.bookingRepository = bookingRepository;
	}

	public void saveBooking(final Booking booking) {
		this.bookingRepository.save(booking);
	}

	public Iterable<Booking> findAll() {
		return this.bookingRepository.findAll();
	}


    @Transactional
    public void save(Booking booking){
		this.bookingRepository.save(booking);
	}
	
	@Transactional
	public Optional<Booking> findById(Integer id){
		return this.bookingRepository.findById(id);
	}
}
