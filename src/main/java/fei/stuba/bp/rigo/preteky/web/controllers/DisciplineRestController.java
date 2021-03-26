package fei.stuba.bp.rigo.preteky.web.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fei.stuba.bp.rigo.preteky.models.sql.Discipline;
import fei.stuba.bp.rigo.preteky.models.sql.QualificationSettings;
import fei.stuba.bp.rigo.preteky.models.sql.Race;
import fei.stuba.bp.rigo.preteky.service.service.DisciplineService;
import fei.stuba.bp.rigo.preteky.service.service.RaceService;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Integer.parseInt;


@RestController
@RequestMapping("/disciplines")
public class DisciplineRestController {
    private DisciplineService disciplineService;
    private RaceService raceService;
    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    private AtomicInteger at = new AtomicInteger(0);

    public DisciplineRestController(DisciplineService disciplineService, RaceService raceService) {
        this.disciplineService = disciplineService;
        this.raceService = raceService;


    }
    @ModelAttribute("activeRace")
    public Race activeRace(){
        if(raceService.getActiveRace().size()>0){
            return raceService.getActiveRace().get(0);
        }else{
            return raceService.getFakeRace();
        }
    }
    @GetMapping(value = "/dates")
    public List<Date> getDates(@ModelAttribute("activeRace") Race activeRace){
        return  getDatesBetweenUsingJava7(activeRace.getStartDate(),activeRace.getEndDate());
    }
    @GetMapping(value ="/disciplineTypes")
    public List<String> getDisciplineTypes(@ModelAttribute("activeRace") Race activeRace){
        return resourcesForRefresh("disciplines");
    }
    @GetMapping(value ="/categories")
    public List<String> getCategories(@ModelAttribute("activeRace") Race activeRace){
        return resourcesForRefresh("categories");
    }
    @PostMapping(value ="/disciplines")
    public List<Discipline> getDisciplinesWithSameName(@RequestBody ObjectNode jsonNodes){
        return disciplineService.findDisciplinesByDisciplineNameAndCategoryAndRaceIdAndIdIsNot(jsonNodes.get("disciplineName").asText(),jsonNodes.get("disciplineCategory").asText(),activeRace().getId(),jsonNodes.get("id").asInt()); // TODO
    }

    @PostMapping(value ="/save")
    public String saveDiscipline(@RequestBody ObjectNode jsonNodes) throws ParseException {
        if(alreadyExist(jsonNodes.get("disciplineName").asText(), jsonNodes.get("category").asText(), activeRace().getId(),jsonNodes.get("id").asInt(),jsonNodes.get("phaseName").asText(), jsonNodes.get("phaseNumber").asInt())){
        java.sql.Date date = new java.sql.Date(format.parse(jsonNodes.get("date").asText()).getTime());

        String idCamera=null;
        if(jsonNodes.get("disciplineType").asText().equals("run")){
            List<Discipline> disciplines= disciplineService.findDisciplinesByRaceIdOrderByCameraIdDesc(activeRace().getId());
            if(!disciplines.isEmpty()){
                at.set(parseInt(disciplines.get(0).getCameraId()));
            }
            idCamera=String.valueOf(at.incrementAndGet());
        }
        QualificationSettings qualificationSettings = new QualificationSettings(
                disciplineService.findDisciplineById(jsonNodes.get("aim").asInt()),
                jsonNodes.get("Q").asInt(),
                jsonNodes.get("q").asInt() );
        Discipline discipline = new Discipline(
                activeRace(),
                jsonNodes.get("disciplineName").asText(),
                jsonNodes.get("note").asText(),
                jsonNodes.get("disciplineTime").asText(),
                jsonNodes.get("category").asText(),
                0,
                jsonNodes.get("disciplineType").asText(),
                idCamera,
                date,
                jsonNodes.get("phaseName").asText(),
                jsonNodes.get("phaseNumber").asInt()
                );
        disciplineService.saveDiscipline(discipline);qualificationSettings.setDiscipline(discipline);
        disciplineService.saveQualificationSettings(qualificationSettings);
        return "Post successfully";
        }else{
            return "Already exist";
        }
    }
    private List<String> resourcesForRefresh(String type){
        List<String> arrayList = new ArrayList<>();
        List<Discipline> disciplines = disciplineService.findDisciplinesByRaceId(activeRace().getId());
        if(type.equals("disciplines")){
            arrayList.add("Disciplína");
        }else if(type.equals("categories")){
            arrayList.add("Kategória");
        }
        int counter = 0;
        String temp = null;
        for (Discipline discipline : disciplines){
            counter = 0;
            for (String object : arrayList){
                if(type.equals("categories")){
                    if(discipline.getCategory().equals(object)){
                        counter++;
                    }else{
                        temp = discipline.getCategory();
                    }
                }
                else if(type.equals("disciplines")){
                    if(discipline.getDisciplineName().equals(object)){
                        counter++;
                    }else{
                        temp = discipline.getDisciplineName();
                    }
                }
            }
            if(counter==0){ arrayList.add(temp);}
        }
        return arrayList;
    }
    private List<java.util.Date> getDatesBetweenUsingJava7(
            java.sql.Date startDate, java.sql.Date endDate) {
        java.util.Date utilStartDate = new java.util.Date(startDate.getTime());
        java.util.Date utilEndDate = new java.util.Date(endDate.getTime());
        List<java.util.Date> datesInRange = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(utilStartDate);

        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(utilEndDate);

        while (calendar.before(endCalendar)) {
            java.util.Date result = calendar.getTime();
            datesInRange.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        datesInRange.add(utilEndDate);
        return datesInRange;
    }
    private Boolean alreadyExist(String disciplineName, String disciplineCategory, int raceId, int disciplineId, String phaseName, int phaseNumber){
        List<Discipline> disciplines = disciplineService.findDisciplinesByDisciplineNameAndCategoryAndRaceIdAndIdIsNotAndPhaseNameAndPhaseNumber(disciplineName,
                disciplineCategory,raceId,disciplineId,phaseName,phaseNumber);
        return disciplines.size() == 0;
    }
    public int getNextCountValue() {
        return at.incrementAndGet();
    }
}
