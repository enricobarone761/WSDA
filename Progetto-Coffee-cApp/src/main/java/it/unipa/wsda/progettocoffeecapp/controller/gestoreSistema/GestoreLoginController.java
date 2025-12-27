package it.unipa.wsda.progettocoffeecapp.controller.gestoreSistema;

import it.unipa.wsda.progettocoffeecapp.model.Utente;
import it.unipa.wsda.progettocoffeecapp.service.UtenteService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class GestoreLoginController {

    private final UtenteService utenteService;

    public GestoreLoginController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @PostMapping("/login-gestore")
    public String login(@RequestParam String email, 
                        @RequestParam String password) {
        
        Optional<Utente> utenteOpt = utenteService.findByUsername(email);

        if (utenteOpt.isPresent()) {
            return "redirect:/Gestore_sistema/gestore_sistema.html";
        } else {
            return "redirect:/Gestore_sistema/login_gestore_sistema.html?error=true";
        }
    }
}
