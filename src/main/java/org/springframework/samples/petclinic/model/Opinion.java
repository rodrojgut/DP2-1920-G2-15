
package org.springframework.samples.petclinic.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Data
public class Opinion extends NamedEntity {

	@NotNull
	@Range(min = 1, max = 5)
	private Integer			puntuation;

	private String			comentary;

	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	private LocalDateTime	date;

	//Relations with Owner and Vet
	@ManyToOne()
	private User			user;

	@ManyToOne(cascade = CascadeType.MERGE)
	private Vet				vet;
}
