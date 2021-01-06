package fei.stuba.bp.rigo.preteky.web.dto;

import fei.stuba.bp.rigo.preteky.models.sql.Race;
import lombok.Data;


import java.sql.Date;
@Data
public class RaceRegistrationDto {

    private Integer activity = 0;

    private String raceName;

    private String place;

    private String organizer;

    private String resultsManager;

    private String phone;

    private Date startDate;

    private Date endDate;

    /**
     * typ preteku-
     * True - vonku
     * False - hala
     * default- vonku
     */
    private Integer raceType = 1;

    private String note;

    private String director;

    private String arbitrator;

    private String technicalDelegate;

    public RaceRegistrationDto() {

    }

    public void checkForNulls(){
        if(this.raceType==null){
            this.raceType=1;
        }if(this.note==null){
            this.note="žiadna poznámka";
        }if(this.arbitrator==null){
            this.arbitrator="žiadny";
        }if(this.technicalDelegate==null){
            this.technicalDelegate="žiadny";
        }
    }

    public RaceRegistrationDto(Race race) {
        this.activity = race.getActivity();
        this.raceName = race.getRaceName();
        this.place = race.getPlace();
        this.organizer = race.getOrganizer();
        this.resultsManager = race.getResultsManager();
        this.phone = race.getPhone();
        this.startDate = race.getStartDate();
        this.endDate = race.getEndDate();
        this.raceType = race.getRaceType();
        this.note = race.getNote();
        this.director = race.getDirector();
        this.arbitrator = race.getArbitrator();
        this.technicalDelegate = race.getTechnicalDelegate();
    }
}
