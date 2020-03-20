
package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

	@Id
	String	username;

	String	password;

	boolean	enabled;

	/*
	 * //Relations with Opinion
	 * 
	 * @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	 * private Collection<Opinion> opinions;
	 * 
	 * 
	 * // Remove Opinion
	 * public void removeOpinion(final Opinion opinion) {
	 * this.opinions.remove(opinion);
	 * }
	 */
}
