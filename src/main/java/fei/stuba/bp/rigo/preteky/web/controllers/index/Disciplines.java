package fei.stuba.bp.rigo.preteky.web.controllers.index;

import fei.stuba.bp.rigo.preteky.models.sql.Discipline;
import fei.stuba.bp.rigo.preteky.models.sql.Race;
import fei.stuba.bp.rigo.preteky.service.service.DisciplineService;
import fei.stuba.bp.rigo.preteky.service.service.RaceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class Disciplines {
    private RaceService raceService;
    private DisciplineService disciplineService;
    public Disciplines(RaceService raceService,DisciplineService disciplineService){
        super();
        this.raceService = raceService;
        this.disciplineService = disciplineService;
    }
    @ModelAttribute("activeRace")
    public Race activeRace(){
        if(raceService.getActiveRace().size()>0){
            return raceService.getActiveRace().get(0);
        }else{
           return raceService.getFakeRace();
        }
    }
    @PostMapping("/disciplines/save")
    public String saveDiscipline(@ModelAttribute("activeRace") Race activeRace,
                                 @ModelAttribute("discipline") Discipline discipline){
        if(discipline.getId()!=null){
            Discipline original = disciplineService.findDisciplineById(discipline.getId());
            original.editDiscipline(discipline);
            disciplineService.saveDiscipline(original);
        }else{
        discipline.setRace(activeRace);
        disciplineService.saveDiscipline(discipline);
        }
        return "redirect:/disciplines";
    }
    @GetMapping("/disciplines/edit/{id}")
    public String disciplineEdit(@PathVariable Integer id,Model model){
        model.addAttribute("discipline",disciplineService.findDisciplineById(id));
        return "disciplines/editDiscipline";
    }
    @GetMapping("disciplines/delete/{id}")
    public String disciplineDelete(@PathVariable Integer id){
        disciplineService.deleteDiscipline(disciplineService.findDisciplineById(id));
        return "redirect:/disciplines";
    }
    @GetMapping("/disciplines")
    public String disciplines(@ModelAttribute("activeRace")
                                      Race activeRace, Model model){
        Discipline discipline = new Discipline();
        model.addAttribute("discipline",discipline);
        model.addAttribute("disciplines",disciplineService.getAllDisciplinesByRaceId(activeRace.getId()));
        return "disciplines/disciplines";
    }
    @GetMapping("disciplines/manage/phases/{id}")
    public String managePhases(@ModelAttribute("activeRace")
                                      Race activeRace,@PathVariable Integer id, Model model){
        model.addAttribute("discipline",disciplineService.findDisciplineById(id));
        return "disciplines/phases";
    }

}
