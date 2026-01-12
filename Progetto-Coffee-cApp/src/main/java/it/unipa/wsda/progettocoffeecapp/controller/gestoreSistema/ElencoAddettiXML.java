package it.unipa.wsda.progettocoffeecapp.controller.gestoreSistema;

import it.unipa.wsda.progettocoffeecapp.model.Utente;
import it.unipa.wsda.progettocoffeecapp.service.UtenteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;

@RestController
@RequestMapping("/elenco-addetti")
public class ElencoAddettiXML {

    private final TemplateEngine templateEngine;
    private final UtenteService utenteService;

    public ElencoAddettiXML(TemplateEngine templateEngine, UtenteService utenteService) {
        this.templateEngine = templateEngine;
        this.utenteService = utenteService;
    }

    @GetMapping(produces = "application/xml")
    public String getAddettiXml() {
        List<Utente> addetti = utenteService.getAllAddetti();
        Context context = new Context();
        context.setVariable("addetti", addetti);
        return templateEngine.process("templateXML/addetto_xml_template", context);
    }
}
