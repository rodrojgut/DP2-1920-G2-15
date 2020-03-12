
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Enfermedad;
import org.springframework.stereotype.Repository;

@Repository
public interface EnfermedadRepository extends CrudRepository<Enfermedad, Integer> {

  @Override
  Collection<Enfermedad> findAll() throws DataAccessException;

}
