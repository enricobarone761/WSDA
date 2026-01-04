package it.unipa.wsda.progettocoffeecapp.controller.cliente;

import it.unipa.wsda.progettocoffeecapp.model.Utente;
import it.unipa.wsda.progettocoffeecapp.service.UtenteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class CreditoController {

    private final UtenteService utenteService;
    public CreditoController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @GetMapping("/credito")
    public ResponseEntity<Double> getCredito(@RequestParam("id_utente") Integer idUtente) {
        Optional<Utente> utente = utenteService.findById(idUtente);
        if (utente.isPresent()) {
            return ResponseEntity.ok(utente.get().getCredito_residuo());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
