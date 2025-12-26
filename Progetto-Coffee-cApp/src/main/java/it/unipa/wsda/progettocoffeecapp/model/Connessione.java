package it.unipa.wsda.progettocoffeecapp.model;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Data
public class Connessione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_connessione;

    @ManyToOne
    @JoinColumn(name = "id_utente", nullable = false, unique = true)
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "id_distributore", nullable = false, unique = true)
    private Distributore distributore;

    @CreationTimestamp
    @Column(name = "data_inizio", nullable = false, updatable = false)
    private Timestamp dataInizio;

    @Column(name = "data_fine")
    private Timestamp dataFine;
}
