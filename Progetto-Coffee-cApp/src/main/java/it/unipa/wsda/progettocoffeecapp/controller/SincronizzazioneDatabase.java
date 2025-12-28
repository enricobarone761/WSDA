package it.unipa.wsda.progettocoffeecapp.controller;

import it.unipa.wsda.progettocoffeecapp.model.StatiDistributori;
import it.unipa.wsda.progettocoffeecapp.service.DistributoreService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/sync")
public class SincronizzazioneDatabase {

    private final DistributoreService distributoreService;
    public SincronizzazioneDatabase(DistributoreService distributoreService) {
        this.distributoreService = distributoreService;
    }

    @PostMapping
    @ResponseBody
    public void syncStatus(@RequestBody List<String> idsGuasti){
        for (String id : idsGuasti) {
            System.out.println("Aggiornamento ricevuto" + id);
            distributoreService.cambiaStatoSenzaSync(id, StatiDistributori.GUASTO);
        }
    }

}
