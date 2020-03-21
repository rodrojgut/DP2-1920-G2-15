
package org.springframework.samples.petclinic.model;

import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;



@Entity
@Table(name = "Diseases")
public class Disease extends BaseEntity {
	
    @NotBlank
    @Column(name = "symptoms")
	private String  symptoms;

	
    @NotBlank
    @Pattern(regexp = "LOW|MEDIUM|HIGH")
    @Column(name = "severity")
    private String severity;
     	
	@NotBlank
    @Column(name = "cure")
    private String  cure;

    @NotNull
    @OneToMany
    @Column(name = "pets")
    private Collection<Pet>  pets;


    public Integer getId() {
        return id;
    }
    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getCure() {
        return cure;
    }

    public void setCure(String cure) {
        this.cure = cure;
    }

    public Collection<Pet> getPets() {
        return pets;
    }

    public void setPets(Collection<Pet> pets) {
        this.pets = pets;
    }

	

}
