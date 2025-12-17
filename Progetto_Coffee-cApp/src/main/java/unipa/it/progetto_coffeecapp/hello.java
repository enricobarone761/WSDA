package unipa.it.progetto_coffeecapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
class hello {
    @RequestMapping("/hello/{name}")
    public String hello(Map<String,Object> model, @PathVariable String name){
        model.put("name", name);
        return "hello";
    }
}



