package it.unipa.wsda.progettocoffeecapp.repository;

import it.unipa.wsda.progettocoffeecapp.model.Addetto;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AddettoRepository extends CrudRepository<Addetto, Integer> {
    Optional<Addetto> getAddettoByEmail(String email);
}
