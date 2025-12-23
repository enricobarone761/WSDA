package it.unipa.wsda.progettocoffeecapp.controller;

import it.unipa.wsda.progettocoffeecapp.model.Utente;
import it.unipa.wsda.progettocoffeecapp.service.DistributoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/distributore")
public class DistributoreController {

    private final DistributoreService distributoreService;

    public DistributoreController(DistributoreService distributoreService) {
        this.distributoreService = distributoreService;
    }

    @GetMapping("/{id}/utente-connesso")
    public ResponseEntity<Utente> getUtenteConnesso(@PathVariable String id) {
        return distributoreService.getUtenteConnesso(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }
}
