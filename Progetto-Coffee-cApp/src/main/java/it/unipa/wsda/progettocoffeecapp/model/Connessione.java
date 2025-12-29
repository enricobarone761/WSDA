package it.unipa.wsda.progettocoffeecapp.model;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Data
public class Connessione {

    @Id
    // trovato su internet come identificare una connessione, generato automaticamente come UUID Universally Unique Identifier.
    @Column(name = "id_connessione", nullable = false, updatable = false, unique = true, length = 36)
    private String id_connessione = java.util.UUID.randomUUID().toString();

    @OneToOne
    @JoinColumn(name = "id_utente", nullable = false)
    private Utente utente;

    @OneToOne
    @JoinColumn(name = "id_distributore", nullable = false)
    private Distributore distributore;

    @CreationTimestamp
    @Column(name = "data_inizio", nullable = false, updatable = false)
    private Timestamp dataInizio;
}
