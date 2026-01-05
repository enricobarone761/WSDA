package it.unipa.wsda.progettocoffeecapp.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_utente;
    private String username;
    private String password;
    private String nome;
    private String cognome;
    private Double credito_residuo;

    @Enumerated(EnumType.STRING)
    private Ruoli ruolo;

}
