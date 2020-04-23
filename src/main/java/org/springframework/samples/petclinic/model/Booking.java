
package org.springframework.samples.petclinic.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "bookings")
public class Booking extends BaseEntity {

	@Column(name = "date")
	@NotNull
	private LocalDateTime	date;

	@NotNull
	@ManyToOne
	private Pet				pet;

	@NotNull
	@ManyToOne
	private Owner			owner;

	@NotNull
	@ManyToOne
	private Vet				vet;

	@NotNull
	@ManyToOne
	private Room			room;


	public LocalDateTime getDate() {
		return this.date;
	}

	public void setDate(final LocalDateTime date) {
		this.date = date;
	}

	public Pet getPet() {
		return this.pet;
	}

	public void setPet(final Pet pet) {
		this.pet = pet;
	}

	public Owner getOwner() {
		return this.owner;
	}

	public void setOwner(final Owner owner) {
		this.owner = owner;
	}

	public Vet getVet() {
		return this.vet;
	}

	public void setVet(final Vet vet) {
		this.vet = vet;
	}

	public Room getRoom() {
		return this.room;
	}

	public void setRoom(final Room room) {
		this.room = room;
	}

}
