package it.unipa.wsda.progettocoffeecapp.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Addetto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_addetto;

    private String nome;
    private String cognome;

    @Column(unique = true)
    private String email;
}
