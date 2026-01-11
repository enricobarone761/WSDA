package it.unipa.wsda.progettocoffeecapp.controller.gestoreSistema;

import it.unipa.wsda.progettocoffeecapp.model.Distributore;
import it.unipa.wsda.progettocoffeecapp.service.DistributoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gestione-distributori")
public class GestioneDistributoriController {

    private final DistributoreService distributoreService;

    public GestioneDistributoriController(DistributoreService distributoreService) {
        this.distributoreService = distributoreService;
    }

    @PostMapping("/aggiungi")
    public ResponseEntity<String> aggiungiDistributore(@RequestBody Distributore distributore) {
        try {
            distributoreService.saveDistributore(distributore);
            return ResponseEntity.ok("Distributore aggiunto con successo");
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @DeleteMapping("/rimuovi")
    public ResponseEntity<String> rimuoviDistributore(@RequestParam String id) {
        try {
            distributoreService.deleteDistributoreById(id);
            return ResponseEntity.ok("Distributore rimosso con successo");
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
