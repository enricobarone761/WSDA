package it.unipa.wsda.progettocoffeecapp.controller.adettoManutezione;

import it.unipa.wsda.progettocoffeecapp.model.Utente;
import it.unipa.wsda.progettocoffeecapp.service.UtenteService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
                        HttpServletRequest request,
                        HttpSession session) {

        try {
            // Verifica se l'utente esiste
            Optional<Utente> utenteOpt = utenteService.findByUsername(username);
            if (utenteOpt.isEmpty()) {
                return "redirect:/Addetto_manutenzione/login_addetto_manutenzione.html?error=UserNotFound";
            }

            // Autentica l'utente con request.login() - gestisce automaticamente tutto
            request.login(username, password);

            // Aggiungi l'utente alla sessione
            Utente utente = utenteOpt.get();
            session.setAttribute("utente", utente);

            return "redirect:/controllo-distributore";

        } catch (ServletException e) {
            return "redirect:/Addetto_manutenzione/login_addetto_manutenzione.html?error=AuthenticationFailed";
        }
    }

    @GetMapping("/controllo-distributore")
    public String showControlloDistributore(HttpSession session, Model model) {
        Utente utente = (Utente) session.getAttribute("utente");
        if (utente != null) {
            model.addAttribute("nome", utente.getNome());
            model.addAttribute("cognome", utente.getCognome());
        }
        return "controllo_distributore";
    }
}
