package it.unipa.wsda.progettocoffeecapp.repository;

import it.unipa.wsda.progettocoffeecapp.model.Bevanda;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BevandaRepository extends CrudRepository<Bevanda, Integer> {
}
