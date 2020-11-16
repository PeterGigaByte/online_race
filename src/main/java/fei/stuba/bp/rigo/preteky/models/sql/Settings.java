package fei.stuba.bp.rigo.preteky.models.sql;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * nastavenia pretekov
 */
@Entity
@Data
@Table(name = "settings")
public class Settings implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * nastavenia dráh - radenie do dráh a počet dráh
     */
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "id_Track",referencedColumnName = "id")
    private Track track;

    @Column(name = "camera_type", nullable = false)
    private String cameraType = "OMEGA";

    /**
     * Pretek je v hale alebo vonku?
     */
    @Column(name = "type_race", nullable = false)
    private Integer typeRace = 1;

    /**
     * typ bodovania, môže byť napríklad
     * súťaž družstiev
     * kde získava pretekár body pre klub
     */
    @Column(name = "type_scoring", nullable = false)
    private String typeScoring = "žiadne";

    /**
     * ak bude mimo súťaž, čo s ním?
     * TRUE - bude posledný vždy
     * FALSE - bude rátaný ako normálny pretekár
     */
    @Column(name = "out_competition", nullable = false)
    private Integer outCompetition = 1;

    /**
     * merajú sa aj reakcie?
     * TRUE - ano
     * FALSE - nie
     */
    @Column(name = "reactions", nullable = false)
    private Integer reactions = 0;

    public Settings() {
    }

    public Settings(String cameraType, Integer typeRace, String typeScoring, Integer outCompetition, Integer reactions, Track track) {
        this.cameraType = cameraType;
        this.typeRace = typeRace;
        this.typeScoring = typeScoring;
        this.outCompetition = outCompetition;
        this.reactions = reactions;
        this.track=track;
    }
}
