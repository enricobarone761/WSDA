package it.unipa.wsda.progettocoffeecapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class Hello {
    @GetMapping("/hello/{name}")
    public String hello(Model model, @PathVariable String name){
        model.addAttribute("name", name);
        return "hello.html";
    }

}
