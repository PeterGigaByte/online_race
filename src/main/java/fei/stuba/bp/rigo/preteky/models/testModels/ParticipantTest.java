package fei.stuba.bp.rigo.preteky.models.testModels;

import lombok.Data;

@Data
public class ParticipantTest {
    private int lane;
    private int bib;
    private String lastName;
    private String firstName;
    private String shortCutOfClub;
    private int result;     // result
    private String time;    // time
    private int status;     // status

    public ParticipantTest(int lane, int bib, String lastName, String firstName, String shortCutOfClub) {
        this.lane = lane;
        this.bib = bib;
        this.lastName = lastName;
        this.firstName = firstName;
        this.shortCutOfClub = shortCutOfClub;
    }
}
