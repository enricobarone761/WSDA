package it.unipa.wsda.progettocoffeecapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/sync")
class SincronizzazioneDatabase {

    @PostMapping
    public void syncStatus(){
        //TODO qui ricevo la lista di tutti i distr guasti e aggiorno nel mio db
    }

}
