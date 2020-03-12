
package org.springframework.samples.petclinic.model;

import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;



@Entity
@Table(name = "Enfermedades")
public class Enfermedad extends BaseEntity {

	
    @NotBlank
    @Column(name = "sintomas")
	private String  sintomas;

	
    @NotBlank
    @Pattern(regexp = "LEVE|MODERADO|SEVERO")
    @Column(name = "gravedad")
    private String gravedad;
     	
	@NotBlank
    @Column(name = "cura")
    private String  cura;

    @OneToMany
    @Column(name = "pets")
    private Collection<Pet>  pets;


    public String getSintomas() {
        return sintomas;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

    public String getGravedad() {
        return gravedad;
    }

    public void setGravedad(String gravedad) {
        this.gravedad = gravedad;
    }

    public String getCura() {
        return cura;
    }

    public void setCura(String cura) {
        this.cura = cura;
    }

    public Collection<Pet> getPets() {
        return pets;
    }

    public void setPets(Collection<Pet> pets) {
        this.pets = pets;
    }

	

}
