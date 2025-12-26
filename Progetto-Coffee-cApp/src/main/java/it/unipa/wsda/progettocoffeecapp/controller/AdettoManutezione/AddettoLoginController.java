package it.unipa.wsda.progettocoffeecapp.controller.AdettoManutezione;

import it.unipa.wsda.progettocoffeecapp.model.Utente;
import it.unipa.wsda.progettocoffeecapp.service.UtenteService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
                        RedirectAttributes redirectAttributes) {
        
        Optional<Utente> utenteOpt = utenteService.findByUsername(username);
        
        if (utenteOpt.isPresent()) {
            Utente utente = utenteOpt.get();
            redirectAttributes.addFlashAttribute("nome", utente.getNome());
            redirectAttributes.addFlashAttribute("cognome", utente.getCognome());
            return "redirect:/controllo-distributore";
        } else {
            return "redirect:/Addetto_manutenzione/login_addetto_manutenzione.html?error=UserNotFound";
        }
    }

    @GetMapping("/controllo-distributore")
    public String showControlloDistributore() {
        return "controllo_distributore";
    }
}
