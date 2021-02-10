package fei.stuba.bp.rigo.preteky.models.sql;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * disciplína
 */
@Entity
@Table(name = "discipline")
@Data
@ToString
public class Discipline implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @ToString.Exclude
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinColumn(name = "race_id",referencedColumnName = "id")
    @ToString.Exclude
    private Race race;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name= "phases_id" ,referencedColumnName = "id")
    @ToString.Exclude
    private List<Phase> phases = new ArrayList<>();

    @Column(name = "participants")
    @ToString.Exclude
    private Integer participants = 0;

    @Column(name = "discipline_name")
    @ToString.Exclude
    private String disciplineName;

    @Column(name = "note")
    @ToString.Exclude
    private String note;

    @Column(name = "time")
    @ToString.Exclude
    private String time;

    @Column(name = "category")
    @ToString.Exclude
    private String category;

    @Column(name = "discipline_type")
    @ToString.Exclude
    private Integer disciplineType;

    public void refreshDisciplineType(){
        if(disciplineName.contains("Žrď")||disciplineName.contains("Výška")){
            disciplineType=1;
        }
        else if(disciplineName.contains("0m")){
            disciplineType=0;//behy
        }
        else{
            disciplineType=2;//iné technické disciplíny
        }
    }

    public void editDiscipline(Discipline discipline){
        this.disciplineName=discipline.getDisciplineName();
        this.note=discipline.getNote();
        this.time=discipline.getTime();
        this.category=discipline.getCategory();
    }

}
