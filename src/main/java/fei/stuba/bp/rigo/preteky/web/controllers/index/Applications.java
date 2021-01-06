package fei.stuba.bp.rigo.preteky.web.controllers.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Applications {
    public Applications(){
        super();
    }
    @GetMapping("/applications")
    public String disciplines(){
        return "applications/applications";
    }
}
