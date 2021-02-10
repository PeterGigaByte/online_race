package fei.stuba.bp.rigo.preteky.models.sql;

import fei.stuba.bp.rigo.preteky.web.dto.RaceRegistrationDto;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Calendar;

/**
 * zoznam pretekov
 */
@Entity
@Table(name = "race")
@Data
public class Race implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_settings", referencedColumnName = "id")
    private Settings settings;

    /**
     * aktívny pretek
     */
    @Column(name = "activity", nullable = false)
    private Integer activity = 0;

    @Column(name = "race_name", nullable = false)
    private String raceName;

    @Column(name = "place", nullable = false)
    private String place;

    @Column(name = "organizer")
    private String organizer;

    @Column(name = "results_manager", nullable = false)
    private String resultsManager;

    @Column(name = "phone")
    private String phone;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    /**
     * typ preteku-
     * True - vonku
     * False - hala
     * default- vonku
     */
    @Column(name = "race_type", nullable = false)
    private Integer raceType = 1;

    @Column(name = "note")
    private String note;

    @Column(name = "director")
    private String director;

    @Column(name = "arbitrator")
    private String arbitrator;

    @Column(name = "technical_delegate")
    private String technicalDelegate;
    public Race(){

    }
    public Race(Integer activity, String raceName, String place, String organizer,
                String resultsManager, String phone, Date startDate, Date endDate,
                Integer raceType, String note, String director, String arbitrator,
                String technicalDelegate,Settings settings) {
        super();
        this.activity = activity;
        this.raceName = raceName;
        this.place = place;
        this.organizer = organizer;
        this.resultsManager = resultsManager;
        this.phone = phone;
        this.startDate = startDate;
        this.endDate = endDate;
        this.raceType = raceType;
        this.note = note;
        this.director = director;
        this.arbitrator = arbitrator;
        this.technicalDelegate = technicalDelegate;
        this.settings=settings;
    }
    public String returnStartDate(){
        return returnDate(this.startDate);
    }
    public String returnEndDate(){
        return returnDate(this.endDate);
    }
    public String returnDate(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day= cal.get(Calendar.DAY_OF_MONTH);
        int month= cal.get(Calendar.MONTH)+1;
        int year= cal.get(Calendar.YEAR);
        return String.valueOf(day)+"."+String.valueOf(month)+"."+String.valueOf(year);
    }
    public void setRaceEdit(Race race) {
        this.activity = race.activity;
        this.raceName = race.raceName;
        this.place = race.place;
        this.organizer = race.organizer;
        this.resultsManager = race.resultsManager;
        this.phone = race.phone;
        this.startDate = race.startDate;
        this.endDate = race.endDate;
        this.raceType = race.raceType;
        this.note = race.note;
        this.director = race.director;
        this.arbitrator = race.arbitrator;
        this.technicalDelegate = race.technicalDelegate;
        this.settings=race.settings;
    }public void setRaceEdit(RaceRegistrationDto race) {
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

}
