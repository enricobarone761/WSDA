package it.unipa.wsda.progettocoffeecapp.controller.adettoManutezione;

import it.unipa.wsda.progettocoffeecapp.model.Distributore;
import it.unipa.wsda.progettocoffeecapp.service.DistributoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RestController
@RequestMapping("/info_distributori")
public class InfoDistributoreXML {

    private final TemplateEngine templateEngine;
    private final DistributoreService distributoreService;

    public InfoDistributoreXML(TemplateEngine templateEngine, DistributoreService distributoreService) {
        this.templateEngine = templateEngine;
        this.distributoreService = distributoreService;
    }

    @GetMapping(produces = "application/xml")
    public String getDistributoriXml() {
        Iterable<Distributore> distributori = distributoreService.getAllDistributori();
        Context context = new Context();
        context.setVariable("distributori", distributori);
        return templateEngine.process("templateXML/distributori_xml_template", context);
    }
}
