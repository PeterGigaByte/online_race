package fei.stuba.bp.rigo.preteky.web.controllers.index;

import fei.stuba.bp.rigo.preteky.service.service.RaceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class Index {

    private RaceService raceService;


    public Index(RaceService raceService){
        super();
        this.raceService = raceService;

    }

    @GetMapping("/")
    public  String index(){
        return "index/index";
    }

}
