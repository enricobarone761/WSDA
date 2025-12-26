package it.unipa.wsda.progettocoffeecapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private String ruolo;

}
