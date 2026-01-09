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
                        HttpServletRequest request) {

        try {
            Optional<Utente> utenteOpt = utenteService.findByUsername(username);
            if (utenteOpt.isEmpty()) {
                return "redirect:/Addetto_manutenzione/login_addetto_manutenzione.html?error=UserNotFound";
            }

            //prima di fare il login esegue il logout di eventuali altre sessioni precedenti
            request.logout();
            //una nuova sessione viene creata e popolata successivamente coi dati dell'utente (che vengono passati anche alla vista)
            HttpSession newSession = request.getSession(true);

            //request.login() gestisce automaticamente autenticazione + autorizzazione + sessione
            request.login(username, password);

            //dall'introduzione di spring security possiamo lavorare meglio con le sessioni
            //in questo caso salviamo quello che ci serve e lo recuperiamo nel GetMapping successivo
            //aiutandoci a resistere anche al refresh
            newSession.setAttribute("utente", utenteOpt.get());

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
