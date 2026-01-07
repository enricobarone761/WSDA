package it.unipa.wsda.progettocoffeecapp.controller.gestoreSistema;

import it.unipa.wsda.progettocoffeecapp.model.Utente;
import it.unipa.wsda.progettocoffeecapp.service.UtenteService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class GestoreLoginController {

    private final AuthenticationManager authenticationManager;

    public GestoreLoginController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login-gestore")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpServletRequest request) {
        try {
            request.login(username, password);
            return "redirect:/Gestore_sistema/gestore_sistema.html";
        } catch (ServletException e) {
            return "redirect:/Gestore_sistema/login_gestore_sistema.html?error=true";
        }
    }
}

