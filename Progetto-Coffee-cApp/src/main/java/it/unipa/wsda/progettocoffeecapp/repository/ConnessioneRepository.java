package it.unipa.wsda.progettocoffeecapp.repository;

import it.unipa.wsda.progettocoffeecapp.model.Connessione;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnessioneRepository extends CrudRepository<Connessione, Long> {
}
