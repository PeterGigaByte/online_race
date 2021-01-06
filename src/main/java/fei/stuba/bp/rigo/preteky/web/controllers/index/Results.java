package fei.stuba.bp.rigo.preteky.web.controllers.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class Results {
    public Results(){
        super();
    }
    @GetMapping("/results")
    public String disciplines(){
        return "results/results";
    }
}
