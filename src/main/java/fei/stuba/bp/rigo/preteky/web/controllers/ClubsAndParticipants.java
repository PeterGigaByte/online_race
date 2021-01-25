package fei.stuba.bp.rigo.preteky.web.controllers;

import fei.stuba.bp.rigo.preteky.models.sql.Club;

import fei.stuba.bp.rigo.preteky.service.service.ClubParticipantsService;
import fei.stuba.bp.rigo.preteky.service.service.DisciplineService;
import fei.stuba.bp.rigo.preteky.service.service.RaceService;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.ArrayUtils;


import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Controller
public class ClubsAndParticipants {
    private RaceService raceService;
    private DisciplineService disciplineService;
    private ClubParticipantsService clubParticipantsService;
    public ClubsAndParticipants(RaceService raceService,DisciplineService disciplineService, ClubParticipantsService clubParticipantsService){
        super();
        this.raceService = raceService;
        this.disciplineService = disciplineService;
        this.clubParticipantsService = clubParticipantsService;
    }
    @ModelAttribute("newClub")
    public Club newClub(){
        return new Club();
    }
    @ModelAttribute("clubs")
    public List<Club> listClubs(){
        return clubParticipantsService.getAllClubs();
    }

    @GetMapping("/clubs")
    public String clubs()  {
        return "participants&clubs/clubs";
    }
    @GetMapping("/participants")
    public String participants(){
        return "participants&clubs/participants";
    }
    @PostMapping("/clubs/create")
    public String clubCreated(@ModelAttribute("newClub") Club newClub,
                              @RequestParam("logoImage") MultipartFile multiPartFile,
                              RedirectAttributes re) throws IOException{
        String logoName = StringUtils.cleanPath(Objects.requireNonNull(multiPartFile.getOriginalFilename()));
        newClub.setLogo(logoName);
        clubParticipantsService.clubSave(newClub);
        String uploadDir = "./src/main/resources/static/logos/" + newClub.getId();
        Path uploadPath = Paths.get(uploadDir);
        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }try{
            InputStream inputStream = multiPartFile.getInputStream();
            Path filePath = uploadPath.resolve(logoName);
            System.out.println(filePath.toString());
            Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e){
            throw new IOException("Logo sa nepodarilo uložiť" + logoName);
        }
        re.addFlashAttribute("message","Klub bol úspešne uložený.");
        return "redirect:/clubs";
    }

}
