
package org.springframework.samples.petclinic.repository.springdatajpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Opinion;
import org.springframework.samples.petclinic.repository.OpinionRepository;

public interface SpringDataOpinionRepository extends OpinionRepository, Repository<Opinion, Integer> {

	@Query("SELECT opinion FROM Opinion opinion WHERE opinion.user.username = :username")
	Iterable<Opinion> findAllMine(@Param("username") String usurname);

}
