package fei.stuba.bp.rigo.preteky.models.sql;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * zoznam pretekov
 */
@Entity
@Data
@Table(name = "race")
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

}
