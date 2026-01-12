package it.unipa.wsda.progettocoffeecapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LandingPageController {

    @GetMapping("/")
    public String mostraLandingPage() {
        return "landing_page";
    }

    @GetMapping("/403")
    public String mostraErrorPage() {
        return "error/403";
    }
}
