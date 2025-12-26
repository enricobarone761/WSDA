package it.unipa.wsda.progettocoffeecapp.controller.cliente;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GestioneConnessione {

    @PostMapping("/connetti")
    public ResponseEntity<String> connetti(@RequestParam("id_distributore") String idDistributore,
                                           @RequestParam("id_utente") Long idUtente) {
        // Qui potresti aggiungere logica di validazione, verifica se il distributore esiste, ecc.
        // Per ora ritorniamo OK per permettere al frontend di aprire la nuova scheda.
        return ResponseEntity.ok("Connessione riuscita");
    }

    @PostMapping("/disconnetti")
    public ResponseEntity<String> disconnetti() {
        return ResponseEntity.ok("Disconnessione riuscita");
    }
}
