package it.unipa.wsda.progettocoffeecapp.controller.AdettoManutezione;

import it.unipa.wsda.progettocoffeecapp.model.Utente;
import it.unipa.wsda.progettocoffeecapp.service.UtenteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AddettoLoginController {

    private final UtenteService utenteService;

    public AddettoLoginController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @PostMapping("/login-manutentore")
    public String login(@RequestParam String username, 
                        @RequestParam String password,
                        Model model) {
        
        Optional<Utente> utenteOpt = utenteService.findByUsername(username);
        
        if (utenteOpt.isPresent()) {
            Utente utente = utenteOpt.get();
            model.addAttribute("nome", utente.getNome());
            model.addAttribute("cognome", utente.getCognome());
            return "controllo_distributore";
        } else {
            return "redirect:/Addetto_manutenzione/login_addetto_manutenzione.html?error=UserNotFound";
        }
    }
}
