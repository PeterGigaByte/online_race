package fei.stuba.bp.rigo.preteky.web.dto;

import fei.stuba.bp.rigo.preteky.models.sql.Track;
import lombok.Data;

@Data
public class TrackDto {

    private Integer numberOfTracks = 8;


    private Integer one = 6;


    private Integer two = 4;


    private Integer three = 2;


    private Integer four = 1;


    private Integer five = 3;


    private Integer six = 5;


    private Integer seven = 7;


    private Integer eight = 8;


    private Integer nine = 9;


    private Integer ten = 10;

    private Integer typeTrack = 1;

    public TrackDto(Track track) {
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

    public TrackDto() {

    }
}
