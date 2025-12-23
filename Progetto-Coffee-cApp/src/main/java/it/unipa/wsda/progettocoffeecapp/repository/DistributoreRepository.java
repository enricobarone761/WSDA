package it.unipa.wsda.progettocoffeecapp.repository;

import it.unipa.wsda.progettocoffeecapp.model.Distributore;
import it.unipa.wsda.progettocoffeecapp.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistributoreRepository extends CrudRepository<Distributore, String> {
}
