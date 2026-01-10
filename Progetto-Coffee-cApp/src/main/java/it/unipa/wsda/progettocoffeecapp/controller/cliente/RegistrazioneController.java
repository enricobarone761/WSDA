package it.unipa.wsda.progettocoffeecapp.controller.cliente;

import it.unipa.wsda.progettocoffeecapp.service.UtenteService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistrazioneController {

    private final UtenteService utenteService;

    public RegistrazioneController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String nome,
                               @RequestParam String cognome,
                               @RequestParam String email,
                               @RequestParam String password,
                               RedirectAttributes redirectAttributes,
                               HttpServletRequest request,
                               HttpSession session) {
        
        try {
            var utente = utenteService.registraNuovoUtente(nome, cognome, email, password);
            
            request.login(email, password);
            session.setAttribute("utente", utente);

            //viene reindirizzato direttamente al controller per il login
            return "redirect:/connessione-distributore";

        } catch (ServletException e) {
            System.err.println("Colpa di .login()");
            return "redirect:/Cliente/login.html";

        } catch (IllegalArgumentException e) {
            System.err.println("Colpa di .registraNuovoUtente()");
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/Cliente/register.html";
        }
    }
}
