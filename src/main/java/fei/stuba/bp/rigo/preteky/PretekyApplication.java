package fei.stuba.bp.rigo.preteky;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class PretekyApplication {


    public static void main(String[] args) {

        SpringApplication.run(PretekyApplication.class, args);

    }/*
    public static void readLSTRRSLT(List<PhaseTest> phasesTestList) {
        try{
            CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build(); // custom separator
            try (CSVReader reader = new CSVReaderBuilder(
                    new FileReader("C:\\Users\\Peter\\Desktop\\camera\\64\\LSTRRSLT.txt"))
                    .withCSVParser(csvParser)   // custom CSV parser
                    .withSkipLines(1)           // skip the first line, header info
                    .build()) {
                List<String[]> r = reader.readAll();
                r.forEach(x -> System.out.println(Arrays.toString(x)));
                r.forEach(x -> {
                    int idCamera = Integer.parseInt(x[2].replaceAll("\\s+",""));
                    String wind = x[5].replaceAll("\\s+","");
                    String phaseStatus = x[8].replaceAll("\\s+","");
                    for (PhaseTest phaseTest:phasesTestList) {
                        if(idCamera==phaseTest.getIdPhase()){
                            phaseTest.setStatus(phaseStatus);
                            phaseTest.setWind(wind);
                        }
                    }
                });
            }
        } catch (Exception e) {
            System.out.println("function readLSTRRSLT threw exception: "+"'"+e.getMessage()+"'");
        }
    }*/
}
