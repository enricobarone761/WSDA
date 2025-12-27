package it.unipa.wsda.progettocoffeecapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Addetto {

    @Id
    @GeneratedValue()
    private Integer id_addetto;

    private String nome;
    private String cognome;

    @Column(unique = true)
    private String email;
}