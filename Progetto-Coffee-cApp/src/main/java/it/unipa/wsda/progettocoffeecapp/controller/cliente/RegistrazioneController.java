package it.unipa.wsda.progettocoffeecapp.controller.cliente;

import it.unipa.wsda.progettocoffeecapp.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistrazioneController {

    @Autowired
    private UtenteService utenteService;

    @PostMapping("/register")
    public String registerUser(@RequestParam String nome,
                               @RequestParam String cognome,
                               @RequestParam String email,
                               @RequestParam String password,
                               RedirectAttributes redirectAttributes) {
        
        try {
            utenteService.registraNuovoUtente(nome, cognome, email, password);
            redirectAttributes.addFlashAttribute("success", "Registrazione completata con successo!");
            return "redirect:/Cliente/login.html";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/Cliente/register.html";
        }
    }
}
