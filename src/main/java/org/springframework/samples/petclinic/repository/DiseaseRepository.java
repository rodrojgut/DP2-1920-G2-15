
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Disease;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.stereotype.Repository;

@Repository
public interface DiseaseRepository extends CrudRepository<Disease, Integer> {

  @Override
  Collection<Disease> findAll() throws DataAccessException;

  @Query("select d from Disease d where d.id =?1")
	Disease findOnebyId(int id);

 /*@Query("select d.pets from Disease d where d.id =?1")
	Collection<Pet> findPetById(int id);
*/




}
