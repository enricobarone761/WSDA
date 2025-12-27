package it.unipa.wsda.progettocoffeecapp.controller.gestoreSistema;

import it.unipa.wsda.progettocoffeecapp.model.Addetto;
import it.unipa.wsda.progettocoffeecapp.model.Distributore;
import it.unipa.wsda.progettocoffeecapp.service.AddettoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RestController
@RequestMapping("/elenco-addetti")
public class ElencoAddettiXML {

    private final TemplateEngine templateEngine;
    private final AddettoService addettoService;

    public ElencoAddettiXML(TemplateEngine templateEngine, AddettoService addettoService) {
        this.templateEngine = templateEngine;
        this.addettoService = addettoService;
    }

    @GetMapping(produces = "application/xml")
    public String getDistributoriXml() {
        Iterable<Addetto> addetti = addettoService.getAllAddetti();
        Context context = new Context();
        context.setVariable("addetti", addetti);
        return templateEngine.process("addetto_xml_template", context);
    }
}
