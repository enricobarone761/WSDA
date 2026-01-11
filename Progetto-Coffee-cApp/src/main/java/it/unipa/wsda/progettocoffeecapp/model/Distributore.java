package it.unipa.wsda.progettocoffeecapp.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Distributore {

    @Id
    @Column(length = 6)
    private String id_distributore;

    @Enumerated(EnumType.STRING)
    private StatiDistributori stato;

    private Double lat;
    private Double lon;
    private String via;
    private String piano;

    private Integer livello_caffe;
    private Integer livello_latte;
    private Integer livello_zucchero;
    private Integer livello_bicchieri;
    private Integer livello_cioccolato;
    private Integer livello_the;

    private Boolean stato_pompa_acqua;
    private Boolean stato_riscaldatore;
    private Boolean stato_erogatore;
    private Boolean stato_display;
    private Boolean stato_gettoniera;
    private Boolean stato_macina_caffe;

}
