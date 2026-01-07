package it.unipa.wsda.progettocoffeecapp.controller.gestoreSistema;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GestoreLoginController {

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

