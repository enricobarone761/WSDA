package it.unipa.wsda.progettocoffeecapp.controller.gestoreSistema;

import it.unipa.wsda.progettocoffeecapp.service.UtenteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/gestione-addetti")
public class GestioneAddettiController {

    private final UtenteService utenteService;

    public GestioneAddettiController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @PostMapping("/aggiungi")
    public ResponseEntity<String> aggiungiAddetto(@RequestParam String email) {
        boolean success = utenteService.assegnaRuoloAddetto(email);

        if (success) {
            return ResponseEntity.ok("Ruolo addetto assegnato con successo");
        } else {
            return ResponseEntity.status(404).body("Utente non trovato. L'utente deve essere registrato prima di poter essere promosso ad addetto");
        }
    }

    @DeleteMapping("/rimuovi")
    public ResponseEntity<String> rimuoviAddetto(@RequestParam String email) {
        boolean success = utenteService.rimuoviRuoloAddetto(email);

        if (success) {
            return ResponseEntity.ok("Ruolo addetto rimosso con successo");
        } else {
            return ResponseEntity.status(404).body("Utente non trovato");
        }
    }
}
