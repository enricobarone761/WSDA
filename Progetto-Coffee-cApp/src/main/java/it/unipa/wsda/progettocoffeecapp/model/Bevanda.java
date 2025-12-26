package it.unipa.wsda.progettocoffeecapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Bevanda {

    @Id
    private Integer id_bevanda;
    private String nome;
    private Double costo;
}
