package fei.stuba.bp.rigo.preteky.models.sql;

import fei.stuba.bp.rigo.preteky.web.dto.TrackDto;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "track")
@Data
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

    public Track() {
    }

    public Track(Integer numberOfTracks, Integer one,
                 Integer two, Integer three, Integer four,
                 Integer five, Integer six, Integer seven,
                 Integer eight, Integer nine, Integer ten,
                 Integer typeTrack) {
        this.numberOfTracks = numberOfTracks;
        this.one = one;
        this.two = two;
        this.three = three;
        this.four = four;
        this.five = five;
        this.six = six;
        this.seven = seven;
        this.eight = eight;
        this.nine = nine;
        this.ten = ten;
        this.typeTrack = typeTrack;
    }
    public void setTrack(TrackDto track){
        this.numberOfTracks = track.getNumberOfTracks();
        this.one = track.getOne();
        this.two = track.getTwo();
        this.three = track.getThree();
        this.four = track.getFour();
        this.five = track.getFive();
        this.six = track.getSix();
        this.seven = track.getSeven();
        this.eight = track.getEight();
        this.nine = track.getNine();
        this.ten = track.getTen();
        this.typeTrack = track.getTypeTrack();
    }

}
