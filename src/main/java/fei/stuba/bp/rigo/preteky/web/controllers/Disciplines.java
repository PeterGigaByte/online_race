package fei.stuba.bp.rigo.preteky.web.controllers;

import fei.stuba.bp.rigo.preteky.models.sql.Discipline;
import fei.stuba.bp.rigo.preteky.models.sql.Phase;
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
    public String managePhases(@ModelAttribute("activeRace") Race activeRace
                            ,@PathVariable Integer id, Model model){
        Discipline discipline = disciplineService.findDisciplineById(id);
        model.addAttribute("discipline",discipline);
        model.addAttribute("phases",discipline.getPhases());
        model.addAttribute("phase",new Phase());
        return "disciplines/phases";
    }
    @PostMapping("disciplines/manage/phases/{id}/addPhase")
    public String addPhase(@PathVariable Integer id,
                           @ModelAttribute("activeRace") Race activeRace,
                           @ModelAttribute("phase") Phase phase,Model model){
        Discipline discipline = disciplineService.findDisciplineById(id);
        phase.setDiscipline(discipline);
        discipline.getPhases().add(phase);
        disciplineService.saveDiscipline(discipline);
        return managePhases(activeRace,id,model);
    }
    @GetMapping("disciplines/manage/phases/{id}/editPhase/{idPhase}")
    public String editPhase(@PathVariable Integer id,
                            @PathVariable Integer idPhase,
                            @ModelAttribute("activeRace") Race activeRace,
                            Model model){
        model.addAttribute("phase",disciplineService.findPhaseById(idPhase));
        model.addAttribute("discipline",disciplineService.findDisciplineById(id));
        return "disciplines/editPhase";
    }@PostMapping("disciplines/manage/phases/{id}/editPhase/{idPhase}/edited")
    public String editedPhase(@PathVariable Integer id,
                            @PathVariable Integer idPhase,
                            @ModelAttribute("activeRace") Race activeRace,
                              @ModelAttribute("phase") Phase phase,Model model){
        disciplineService.savePhase(phase);
        return managePhases(activeRace,id,model);
    }
    @GetMapping("disciplines/manage/phases/{id}/deletePhase/{idPhase}")
    public String deletePhase(@PathVariable Integer id,
                              @PathVariable Integer idPhase,
                              @ModelAttribute("activeRace") Race activeRace,
                             Model model){
        disciplineService.removePhase(id,idPhase);
        return managePhases(activeRace,id,model);
    }
}
