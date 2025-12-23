package it.unipa.wsda.progettocoffeecapp.controller;

import it.unipa.wsda.progettocoffeecapp.model.Bevanda;
import it.unipa.wsda.progettocoffeecapp.model.Utente;
import it.unipa.wsda.progettocoffeecapp.service.BevandaService;
import it.unipa.wsda.progettocoffeecapp.service.DistributoreService;
import it.unipa.wsda.progettocoffeecapp.service.UtenteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/erogazione")
public class ErogazioneController {

    private final DistributoreService distributoreService;
    private final UtenteService utenteService;
    private final BevandaService bevandaService;

    public ErogazioneController(DistributoreService distributoreService, UtenteService utenteService, BevandaService bevandaService) {
        this.distributoreService = distributoreService;
        this.utenteService = utenteService;
        this.bevandaService = bevandaService;
    }

    @PostMapping("/{idDistributore}/eroga")
    public ResponseEntity<?> erogaBevanda(@PathVariable String idDistributore, @RequestBody Map<String, Integer> request) {
        Integer idBevanda = request.get("idBevanda");
        if (idBevanda == null) {
            return ResponseEntity.badRequest().body("ID bevanda non specificato");
        }

        Optional<Utente> utenteConnesso = distributoreService.getUtenteConnesso(idDistributore);

        if (utenteConnesso.isEmpty()) {
            return ResponseEntity.status(401).body("Nessun utente connesso al distributore");
        }

        Optional<Bevanda> bevandaOpt = bevandaService.getBevanda(idBevanda);
        if (bevandaOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Bevanda non trovata");
        }

        Bevanda bevanda = bevandaOpt.get();
        Utente utente = utenteConnesso.get();
        boolean successo = utenteService.scalaCredito(utente.getId_utente(), bevanda.getCosto());

        if (successo) {
            return ResponseEntity.ok("Erogazione in corso...");
        } else {
            return ResponseEntity.status(402).body("Credito insufficiente");
        }
    }
}
