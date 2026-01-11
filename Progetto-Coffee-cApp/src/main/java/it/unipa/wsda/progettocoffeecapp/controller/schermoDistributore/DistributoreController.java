package it.unipa.wsda.progettocoffeecapp.controller.schermoDistributore;

import it.unipa.wsda.progettocoffeecapp.model.Utente;
import it.unipa.wsda.progettocoffeecapp.service.DistributoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
//@CrossOrigin(origins = "*") // questa annotazione mi permette di scambiare dati su porte diversa, intellij mi crea un po di problemi qui
public class DistributoreController {

    private final DistributoreService ds;
    public DistributoreController(DistributoreService distributoreService) {
        this.ds = distributoreService;
    }

    @GetMapping("/distributore/polling-utente")
    public @ResponseBody ResponseEntity<Utente> getUtenteConnesso(@RequestParam String idDistributore) {
        //System.out.println(idDistributore);
        Optional<Utente> utenteOpt = ds.getUtenteConnesso(idDistributore);
        if (utenteOpt.isPresent()) {
            return ResponseEntity.ok(utenteOpt.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/interfaccia-distributore")
    public String showInterfacciaDistributore(@RequestParam String idDistributore,
                                              Model model) {

        model.addAttribute("idDistributore", idDistributore);
        return "interfaccia_distributore";
    }
}
