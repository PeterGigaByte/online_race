package fei.stuba.bp.rigo.preteky.models.sql;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "qualification_settings_discipline")
public class QualificationSettings implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_discipline_where",referencedColumnName = "id")
    private Discipline disciplineWhere;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_discipline",referencedColumnName = "id")
    private Discipline discipline;

    @Column(name = "Q_by_place")
    private Integer qByPlace;

    @Column(name = "q_by_time")
    private Integer qByTime;

    public QualificationSettings() {
    }

    public QualificationSettings(Discipline disciplineWhere, Integer qByPlace, Integer qByTime) {
        this.disciplineWhere = disciplineWhere;
        this.qByPlace = qByPlace;
        this.qByTime = qByTime;
    }
}
