package fei.stuba.bp.rigo.preteky.web.controllers.index;

import fei.stuba.bp.rigo.preteky.service.service.Race;
import fei.stuba.bp.rigo.preteky.service.service.Settings;
import fei.stuba.bp.rigo.preteky.service.service.Track;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class Index {

    private Race race;
    private Settings settings;
    private Track track;

    public Index(Race race,Settings settings, Track track){
        super();
        this.race=race;
        this.settings=settings;
        this.track=track;
    }

    @GetMapping("/")
    public  String index(){
        return "index/index";
    }


}
