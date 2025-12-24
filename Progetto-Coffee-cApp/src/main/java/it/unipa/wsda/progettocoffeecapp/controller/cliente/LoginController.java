package it.unipa.wsda.progettocoffeecapp.controller.cliente;

import it.unipa.wsda.progettocoffeecapp.model.Utente;
import it.unipa.wsda.progettocoffeecapp.service.UtenteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController {

    private final UtenteService utenteService;

    public LoginController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, 
                        @RequestParam String password, 
                        Model model) {
        
        Optional<Utente> utenteOpt = utenteService.findByUsername(email);
        
        if (utenteOpt.isPresent()) {
            Utente utente = utenteOpt.get();
            model.addAttribute("utente", utente);
            return "cliente_home";
        } else {
            return "redirect:/Cliente/login.html?error=UserNotFound";
        }
    }
}
