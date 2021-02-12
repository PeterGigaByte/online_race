package fei.stuba.bp.rigo.preteky.models.testModels;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class PhaseTest {
    private String pattern = "dd.MM.yyyy";
    private String patternTime = "HH:mm";
    SimpleDateFormat simpleTimeFormat = new SimpleDateFormat(patternTime);
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    private String status = "Unofficial"; //status LSTRRSLT
    private String wind; //wind LSTRRSLT
    private List<ParticipantTest> participants = new ArrayList<>();
    private int idPhase;
    private String date = simpleDateFormat.format(new Date());
    private String time = simpleTimeFormat.format(new Date());
    private int length;
    private String nameOfPhase;
    private String sponsor = "Peter Rigo Development";

    public PhaseTest(int idPhase, int length, String nameOfPhase) {
        this.idPhase = idPhase;
        this.length = length;
        this.nameOfPhase = nameOfPhase;
    }
}
