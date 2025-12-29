package it.unipa.wsda.progettocoffeecapp.repository;

import it.unipa.wsda.progettocoffeecapp.model.Utente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtenteRepository extends CrudRepository<Utente, Integer> {
    Optional<Utente> findByUsername(String username);
    List<Utente> findByRuolo(String ruolo);
}
