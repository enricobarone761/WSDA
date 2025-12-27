package it.unipa.wsda.progettocoffeecapp.controller.cliente;

import it.unipa.wsda.progettocoffeecapp.service.UtenteService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RicaricaController {

    private final UtenteService utenteService;

    public RicaricaController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @PostMapping("/ricarica")
    public ResponseEntity<Boolean> ricaricaCredito(@RequestParam Integer id_utente,
                                                    @RequestParam Double importo) {
        
        if(utenteService.ricaricaCredito(id_utente, importo)){
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.status(500).body(false);
        }
    }
}
