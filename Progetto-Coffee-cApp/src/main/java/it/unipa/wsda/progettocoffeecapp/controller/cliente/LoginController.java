package it.unipa.wsda.progettocoffeecapp.controller.cliente;

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
public class LoginController {

    private final UtenteService utenteService;

    public LoginController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpServletRequest request,
                        HttpSession session) {
        try {
            Optional<Utente> utenteOpt = utenteService.findByUsername(username);
            if (utenteOpt.isEmpty()) {
                return "redirect:/Cliente/login.html?error=UserNotFound";
            }

            request.login(username, password);
            session.setAttribute("utente", utenteOpt.get());
            return "redirect:/connessione-distributore";

        } catch (ServletException e) {
            return "redirect:/Cliente/login.html?error=AuthenticationFailed";
        }
    }

    @GetMapping("/connessione-distributore")
    public String showConnessioneDistributore(HttpSession session, Model model) {
        Utente utente = (Utente) session.getAttribute("utente");
        if (utente != null) {
            model.addAttribute("utente", utente);
        }
        return "connessione_distributore";
    }
}


