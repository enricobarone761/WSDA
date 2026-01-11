package it.unipa.wsda.progettocoffeecapp.controller.adettoManutezione;

import it.unipa.wsda.progettocoffeecapp.model.StatiDistributori;
import it.unipa.wsda.progettocoffeecapp.service.DistributoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manutenzione")
public class ManutenzioneController {

    private final DistributoreService distributoreService;

    public ManutenzioneController(DistributoreService distributoreService) {
        this.distributoreService = distributoreService;
    }

    @PostMapping("/ripristina-forniture")
    public ResponseEntity<String> ripristinaForniture(@RequestParam String idDistributore) {
        distributoreService.ripristinaForniture(idDistributore);
        return ResponseEntity.ok("Forniture ripristinate con successo");
    }

    @PostMapping("/ripristina-guasti")
    public ResponseEntity<String> ripristinaGuasti(@RequestParam String idDistributore) {
        distributoreService.ripristinaGuasti(idDistributore);
        return ResponseEntity.ok("Guasti ripristinati con successo");
    }

    @PostMapping("/cambia-stato")
    public ResponseEntity<String> cambiaStato(@RequestParam String idDistributore, @RequestParam StatiDistributori stato) {
        try {
            distributoreService.cambiaStato(idDistributore, stato, true);
            return ResponseEntity.ok("Stato aggiornato con successo");
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
