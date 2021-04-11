package fei.stuba.bp.rigo.preteky.models.sql;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "result_start_list")
@Data
public class ResultStartList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_athlete",referencedColumnName = "id")
    private Athlete athlete;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_discipline",referencedColumnName = "id")
    private Discipline discipline;

    @Column(name = "start_performance")
    private Double startPerformance;

    @Column(name = "result_performance")
    private Integer resultPerformance;

    @Column(name = "place")
    private Integer place;

    @Column(name = "line")
    private Integer line;

    @Column(name = "reaction")
    private Integer reaction;

}
