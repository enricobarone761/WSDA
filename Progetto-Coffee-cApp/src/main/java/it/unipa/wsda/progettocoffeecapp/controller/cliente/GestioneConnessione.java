package it.unipa.wsda.progettocoffeecapp.controller.cliente;

import it.unipa.wsda.progettocoffeecapp.service.DistributoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GestioneConnessione {

    private final DistributoreService distributoreService;

    public GestioneConnessione(DistributoreService distributoreService) {
        this.distributoreService = distributoreService;
    }

    @PostMapping("/connetti")
    public ResponseEntity<String> connetti(@RequestParam("id_distributore") String idDistributore,
                                           @RequestParam("id_utente") Integer idUtente) {
        try {
            distributoreService.connetti(idUtente, idDistributore);
            return ResponseEntity.ok("Connessione riuscita");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Errore durante la connessione: " + e.getMessage());
        }
    }

    @PostMapping("/disconnetti")
    public ResponseEntity<String> disconnetti(@RequestParam("id_utente") Integer idUtente) {
        try {
            distributoreService.disconnetti(idUtente);
            return ResponseEntity.ok("Disconnessione riuscita");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Errore durante la disconnessione: " + e.getMessage());
        }
    }
}
