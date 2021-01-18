package fei.stuba.bp.rigo.preteky.models.sql;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * disciplína
 */
@Entity
@Table(name = "discipline")
@Data
public class Discipline implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinColumn(name = "race_id",referencedColumnName = "id")
    private Race race;

    @Column(name = "participants")
    private Integer participants = 0;

    @Column(name = "discipline_name")
    private String disciplineName;

    @Column(name = "note")
    private String note;

    @Column(name = "time")
    private String time;

    @Column(name = "category")
    private String category;

    public void editDiscipline(Discipline discipline){
        this.disciplineName=discipline.getDisciplineName();
        this.note=discipline.getNote();
        this.time=discipline.getTime();
        this.category=discipline.getCategory();
    }

}
