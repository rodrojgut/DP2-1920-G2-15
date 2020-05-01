
package org.springframework.samples.petclinic.model;


import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



@Entity
@Table(name = "bookings")
public class Booking extends BaseEntity {

    @Column(name = "date")
    private LocalDate date;

    
    @ManyToOne
    private Pet pet;
    
   
    @ManyToOne 
    private Owner owner;

    
    @ManyToOne
    private Vet vet;

    
    @ManyToOne
    private Room room;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    

    
}
