
package org.springframework.samples.petclinic.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Data
public class Opinion extends NamedEntity {

	@Range(min = 1, max = 5)
	private Integer			puntuation;

	private String			comentary;

	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	private LocalDateTime	date;

	//Relations with Owner and Vet
	@ManyToOne(cascade = CascadeType.ALL)
	private User			user;

	@ManyToOne(cascade = CascadeType.ALL)
	private Vet				vet;
}
