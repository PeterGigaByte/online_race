package fei.stuba.bp.rigo.preteky.web.controllers.index;

import fei.stuba.bp.rigo.preteky.service.service.RaceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Disciplines {
    public Disciplines(){
        super();
    }
    @GetMapping("/disciplines")
    public String disciplines(){
        return "disciplines/disciplines";
    }
}
