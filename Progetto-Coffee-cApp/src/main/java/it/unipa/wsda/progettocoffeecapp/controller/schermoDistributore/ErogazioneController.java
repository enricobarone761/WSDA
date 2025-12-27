package it.unipa.wsda.progettocoffeecapp.controller.schermoDistributore;

import it.unipa.wsda.progettocoffeecapp.model.Bevanda;
import it.unipa.wsda.progettocoffeecapp.model.Utente;
import it.unipa.wsda.progettocoffeecapp.service.BevandaService;
import it.unipa.wsda.progettocoffeecapp.service.DistributoreService;
import it.unipa.wsda.progettocoffeecapp.service.UtenteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/{id_distributore}/eroga/{id_bevanda}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> erogaBevanda(@PathVariable String id_distributore, @PathVariable Integer id_bevanda) {
        
        //1 controlliamo l'utente connesso
        Optional<Utente> utenteConnesso = distributoreService.getUtenteConnesso(id_distributore);

        if (utenteConnesso.isEmpty()) {
            return ResponseEntity.status(401).body("Nessun utente connesso al distributore");
        }

        //2 recuperiamo costo bevanda
        Optional<Bevanda> bevandaOpt = bevandaService.getBevanda(id_bevanda);
        if (bevandaOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Bevanda non trovata");
        }

        Bevanda bevanda = bevandaOpt.get();
        Utente utente = utenteConnesso.get();
        boolean successo = utenteService.scalaCredito(utente.getId_utente(), bevanda.getCosto());

        if (successo) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(402).body("Credito insufficiente");
        }
    }
}
