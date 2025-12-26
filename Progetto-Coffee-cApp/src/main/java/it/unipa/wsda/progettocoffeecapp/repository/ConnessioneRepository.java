package it.unipa.wsda.progettocoffeecapp.repository;

import it.unipa.wsda.progettocoffeecapp.model.Connessione;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConnessioneRepository extends CrudRepository<Connessione, Integer> {
    @Query("SELECT c FROM Connessione c WHERE c.distributore.id_distributore = :id AND c.dataFine IS NULL")
    Optional<Connessione> findByDistributoreId_distributoreAndDataFineIsNull(@Param("id") String id);

    @Query("SELECT c FROM Connessione c WHERE c.utente.id_utente = :idUtente AND c.dataFine IS NULL")
    Optional<Connessione> findByUtenteId(@Param("idUtente") Integer idUtente);
}
