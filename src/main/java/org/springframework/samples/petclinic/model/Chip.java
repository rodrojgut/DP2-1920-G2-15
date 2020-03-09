
package org.springframework.samples.petclinic.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "chips")
public class Chip extends BaseEntity {

	@NotEmpty
	@Column(name = "serial_number")
	private String	serialNumber;

	@NotEmpty
	@Column(name = "model")
	private String	model;

	@NotEmpty
	@Column(name = "geolocatable")
	private Boolean	geolocatable;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "pet_id", referencedColumnName = "pet_id")
	private Pet		pet;


	public String getSerialNumber() {
		return this.serialNumber;
	}

	public void setSerialNumber(final String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(final String model) {
		this.model = model;
	}

	public Boolean getGeolocatable() {
		return this.geolocatable;
	}

	public void setGeolocatable(final Boolean geolocatable) {
		this.geolocatable = geolocatable;
	}

	public Pet getPet() {
		return this.pet;
	}

	public void setPet(final Pet pet) {
		this.pet = pet;
	}
}
