package it.unipa.wsda.progettocoffeecapp.controller.schermoDistributore;

import it.unipa.wsda.progettocoffeecapp.model.Utente;
import it.unipa.wsda.progettocoffeecapp.service.DistributoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/distributore")
@CrossOrigin(origins = "*") // questa annotazione mi permette di scambiare dati su porte diversa, intellij mi crea un po di problemi qui
public class DistributoreController {

    private final DistributoreService distributoreService;

    public DistributoreController(DistributoreService distributoreService) {
        this.distributoreService = distributoreService;
    }

    @GetMapping("/polling-utente")
    public ResponseEntity<Utente> getUtenteConnesso(@RequestParam String idDistributore) {
        //System.out.println(idDistributore);
        return distributoreService.getUtenteConnesso(idDistributore)
                .map(utente -> ResponseEntity.ok(utente))
                .orElse(ResponseEntity.noContent().build());
    }
}
