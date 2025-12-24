package it.unipa.wsda.progettocoffeecapp.controller.cliente;

import it.unipa.wsda.progettocoffeecapp.model.Utente;
import it.unipa.wsda.progettocoffeecapp.service.UtenteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class RicaricaController {

    private final UtenteService utenteService;

    public RicaricaController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @PostMapping("/ricarica")
    public String ricaricaCredito(@RequestParam Integer id_utente,
                                  @RequestParam Integer importo,
                                  Model model) {
        
        utenteService.ricaricaCredito(id_utente, importo);
        
        Optional<Utente> utenteOpt = utenteService.findById(id_utente);
        if (utenteOpt.isPresent()) {
            model.addAttribute("utente", utenteOpt.get());
            return "connessione_distributore";
        } else {
            return "redirect:/Cliente/login.html?error=UserNotFound";
        }
    }
}
