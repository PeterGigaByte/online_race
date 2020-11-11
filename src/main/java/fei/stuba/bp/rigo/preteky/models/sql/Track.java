package fei.stuba.bp.rigo.preteky.models.sql;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "track")
public class Track implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "number_of_tracks")
    private Integer numberOfTracks = 8;

    @Column(name = "one")
    private Integer one = 6;

    @Column(name = "two")
    private Integer two = 4;

    @Column(name = "three")
    private Integer three = 2;

    @Column(name = "four")
    private Integer four = 1;

    @Column(name = "five")
    private Integer five = 3;

    @Column(name = "six")
    private Integer six = 5;

    @Column(name = "seven")
    private Integer seven = 7;

    @Column(name = "eight")
    private Integer eight = 8;

    @Column(name = "nine")
    private Integer nine;

    @Column(name = "ten")
    private Integer ten;

    @Column(name = "type_track")
    private Integer typeTrack = 1;

}
