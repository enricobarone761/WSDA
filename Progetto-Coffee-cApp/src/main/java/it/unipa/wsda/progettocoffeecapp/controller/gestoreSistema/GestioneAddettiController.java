package it.unipa.wsda.progettocoffeecapp.controller.gestoreSistema;

import it.unipa.wsda.progettocoffeecapp.model.Addetto;
import it.unipa.wsda.progettocoffeecapp.service.AddettoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gestione-addetti")
public class GestioneAddettiController {

    private final AddettoService addettoService;

    public GestioneAddettiController(AddettoService addettoService) {
        this.addettoService = addettoService;
    }

    @PostMapping("/aggiungi")
    public ResponseEntity<String> aggiungiAddetto(@RequestBody Addetto addetto) {
        addettoService.saveAddetto(addetto);
        return ResponseEntity.ok("Addetto aggiunto con successo");
    }

    @DeleteMapping("/rimuovi")
    public ResponseEntity<String> rimuoviAddetto(@RequestParam String email) {
        addettoService.deleteAddettoByEmail(email);
        return ResponseEntity.ok("Addetto rimosso con successo");
    }
}
