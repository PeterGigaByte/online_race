package fei.stuba.bp.rigo.preteky.models.sql;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "phase")
@Data
public class Phase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "discipline_id",referencedColumnName = "id")
    private Discipline discipline;

    @Column(name = "camera_id")
    private Integer cameraId;

    @Column(name = "time")
    private String time;

    @Column(name = "participants")
    private Integer participants;

    @Column(name = "phase_name")
    private String phaseName;

    @Column(name = "phase_number")
    private Integer phaseNumber;

    @Column(name = "note")
    private String note;

    @Column(name = "settings_Q_place")
    private Integer settingsQPlace;

    @Column(name = "settings_q_time")
    private Integer settingsQTime;

    @Column(name = "settings_id_advance")
    private Integer settingsIdAdvance;

    @Column(name = "day")
    private Date day;


}
