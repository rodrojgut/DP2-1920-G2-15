
package org.springframework.samples.petclinic.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Rooms")
public class Room extends BaseEntity {

    @NotNull
    @Column(name = "floor")
	private Integer floor;

    @NotEmpty
    @ElementCollection
    @Column(name = "medical_team")
    private Collection<String> medicalTeam;
    

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Collection<String> getMedicalTeam() {
        return medicalTeam;
    }

    public void setMedicalTeam(Collection<String> medicalTeam) {
        this.medicalTeam = medicalTeam;
    }

	

    
    }