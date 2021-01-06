package fei.stuba.bp.rigo.preteky.web.dto;

import fei.stuba.bp.rigo.preteky.models.sql.Settings;
import lombok.Data;

@Data
public class SettingsDto {



    private String cameraType = "OMEGA";

    /**
     * Pretek je v hale alebo vonku?
     */

    private Integer typeRace = 1;

    /**
     * typ bodovania, môže byť napríklad
     * súťaž družstiev
     * kde získava pretekár body pre klub
     */

    private String typeScoring = "comptetitor_race";

    /**
     * ak bude mimo súťaž, čo s ním?
     * TRUE - bude posledný vždy
     * FALSE - bude rátaný ako normálny pretekár
     */

    private Integer outCompetition = 1;

    /**
     * merajú sa aj reakcie?
     * TRUE - ano
     * FALSE - nie
     */

    private Integer reactions = 0;

    public SettingsDto() {

    }

    public void checkForNulls(){
        if(this.typeRace==null){
            this.typeRace=1;
        }if(this.outCompetition==null){
            this.outCompetition=1;
        }if(this.reactions==null){
            this.reactions=1;
        }
    }

    public SettingsDto(Settings settings) {
        this.cameraType = settings.getCameraType();
        this.typeRace = settings.getTypeRace();
        this.typeScoring = settings.getTypeScoring();
        this.outCompetition = settings.getOutCompetition();
        this.reactions = settings.getReactions();
    }
}

