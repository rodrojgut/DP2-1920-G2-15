package org.springframework.samples.petclinic.model;


import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="bookings")
public class Booking extends BaseEntity {

    @Column(name = "date")
    @NotNull
    private LocalDateTime date;

    @NotNull
    @ManyToOne
    private Pet pet;
    
    @NotNull
    @ManyToOne 
    private Owner owner;

    @NotNull
    @ManyToOne
    private Vet vet;

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Vet getVet() {
        return vet;
    }

    public void setVet(Vet vet) {
        this.vet = vet;
    }

    
}