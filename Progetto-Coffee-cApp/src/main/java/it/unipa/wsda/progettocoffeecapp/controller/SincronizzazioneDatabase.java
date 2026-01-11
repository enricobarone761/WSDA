package it.unipa.wsda.progettocoffeecapp.controller;

import it.unipa.wsda.progettocoffeecapp.model.StatiDistributori;
import it.unipa.wsda.progettocoffeecapp.service.DistributoreService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SincronizzazioneDatabase {

    private final DistributoreService distributoreService;
    public SincronizzazioneDatabase(DistributoreService distributoreService) {
        this.distributoreService = distributoreService;
    }

    @PostMapping("/sync")
    public void syncStatus(@RequestBody List<String> idsGuasti){
        for (String id : idsGuasti) {
            System.out.println("Aggiornamento ricevuto " + id);
            distributoreService.cambiaStato(id, StatiDistributori.GUASTO,false);
        }
    }

}
