package fei.stuba.bp.rigo.preteky;

import com.opencsv.*;
import fei.stuba.bp.rigo.preteky.models.sql.Participant;
import fei.stuba.bp.rigo.preteky.models.sql.Phase;
import fei.stuba.bp.rigo.preteky.models.testModels.ParticipantTest;
import fei.stuba.bp.rigo.preteky.models.testModels.PhaseTest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class PretekyApplication {

    public static void main(String[] args) {
        SpringApplication.run(PretekyApplication.class, args);

        List<PhaseTest> phasesTestList = createSimulationParameters();
        int choice = 0;
        if(choice==0){
            createCsv(phasesTestList);
        }else{
            try{
                createCsvWithLibrary();
            }
            catch (Exception e){
                System.out.println("function createCsvWithLibrary threw exception: "+"'"+e.getMessage()+"'");
            }
        }
        try{
            readLSTRslt(phasesTestList);
        }catch (Exception e){
            System.out.println("function readLSTRslt threw exception: "+"'"+e.getMessage()+"'");
        }
        try{
            readLSTRRSLT(phasesTestList);
        } catch (Exception e) {
            System.out.println("function readLSTRRSLT threw exception: "+"'"+e.getMessage()+"'");
        }
        phasesTestList.forEach(x -> {
            x.getParticipants().forEach(y -> System.out.println(y.toString()));
            System.out.println(x.getNameOfPhase()+"; "+x.getWind()+"; "+x.getStatus()+"\n");
        });



    }
    public static void createCsvWithLibrary() throws IOException {
        FileOutputStream os = new FileOutputStream("C:\\Bakalarska Práca\\Projekt folder\\csv\\STARTLIST.csv");
        os.write(0xef);
        os.write(0xbb);
        os.write(0xbf);
        CSVWriter writer = new CSVWriter(new OutputStreamWriter(os));
        List<String[]> theRows = new ArrayList<>();
        String[] header = new String[]{"id","name","launchdate"};
        theRows.add(header);
        String[] row1 = new String[]{"1","Falcon","12","čech"};
        String[] row2 = new String[]{"2","battle","12","26156"};
        String[] row3 = new String[]{"3","wall","12","26156"};
        theRows.add(row1); theRows.add(row2); theRows.add(row3);
        writer.writeAll(theRows);
        writer.close();
    }
    public static void createCsv(List<PhaseTest> phasesTestList){
        try{
            String path = "C:\\Bakalarska Práca\\Projekt folder\\csv\\STARTLIST.csv";
            FileOutputStream file = new FileOutputStream(path);
            file.write(0xef);//utf-8
            file.write(0xbb);//utf-8
            file.write(0xbf);//utf-8
            OutputStreamWriter fileWriter = new OutputStreamWriter(file);

            System.out.println("New file created !!");

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Event Code;Date;Time;Lane/order;Bib No\r\n");
            //stringBuilder.append(";"); // nový stĺpec
            //stringBuilder.append("\r\n"); //nový riadok
            for (PhaseTest phaseTest : phasesTestList){
                stringBuilder.append(phaseTest.getIdPhase()).append(";");
                stringBuilder.append(phaseTest.getDate()).append(";");
                stringBuilder.append(phaseTest.getTime()).append(";;;;;;");
                stringBuilder.append(phaseTest.getLength()).append(";");
                stringBuilder.append(phaseTest.getNameOfPhase()).append(";");
                stringBuilder.append(phaseTest.getSponsor()).append("\r\n");
                System.out.println("Discipline details written !!");
                for (ParticipantTest participantTest : phaseTest.getParticipants()){
                    stringBuilder.append(";;;");
                    stringBuilder.append(participantTest.getLane()).append(";");
                    stringBuilder.append(participantTest.getBib()).append(";");
                    stringBuilder.append(participantTest.getLastName()).append(";");
                    stringBuilder.append(participantTest.getFirstName()).append(";");
                    stringBuilder.append(participantTest.getShortCutOfClub()).append("\r\n");
                    System.out.println("Participants details written !!");
                }
            }
            fileWriter.write(stringBuilder.toString());
            fileWriter.close();
            System.out.println("File closed !!");
        }catch (Exception e){
            System.out.println("function createCsv threw exception: "+"'"+e.getMessage()+"'");
        }
    }

    public static List<PhaseTest> createSimulationParameters(){
        String[] firstNames = {"Peter","Jano","Miro","Marián","Stano","Patrik","Milan","Jozef"};
        String[] lastNames = {"Faječka","Maječka","Vajenský","Trajenský","Lombardský","Parížsky","Šalátový","Majonézový"};
        String[] clubs = {"ŠOGNR","ASKTT","VTMA","ŠOGNR","ŠMJ","ASKTT","ŠOGNR","AFDE"};
        List<PhaseTest> phasesTestList = new ArrayList<>();
        for(int i = 1;i<3;i++){
            phasesTestList.add(new PhaseTest(i,100,"100m Muži Beh"+ i));
            for (int y = 1;y<9;y++){
                phasesTestList.get(i-1).getParticipants().add(new ParticipantTest(y,y+100,firstNames[y-1],lastNames[y-1],clubs[y-1]));
            }
        }
        return phasesTestList;
    }
    public static void readLSTRslt(List<PhaseTest> phasesTestList) throws IOException {
        CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build(); // custom separator
        try(CSVReader reader = new CSVReaderBuilder(
                new FileReader("C:\\Bakalarska Práca\\Projekt folder\\csv\\LSTRslt.txt"))
                .withCSVParser(csvParser)   // custom CSV parser
                .withSkipLines(1)           // skip the first line, header info
                .build()){
            List<String[]> r = reader.readAll();
            r.forEach(x -> System.out.println(Arrays.toString(x)));
            r.forEach(x -> {
                int idCamera = Integer.parseInt(x[2].replaceAll("\\s+",""));
                int bibNo = Integer.parseInt(x[4].replaceAll("\\s+",""));
                int lane = Integer.parseInt(x[5].replaceAll("\\s+",""));
                int status = Integer.parseInt(x[6].replaceAll("\\s+",""));
                String time = x[8].replaceAll("\\s+","");
                int result = Integer.parseInt(x[9].replaceAll("\\s+",""));
                for (PhaseTest phaseTest:phasesTestList) {
                    System.out.println(idCamera+"; "+phaseTest.getIdPhase());
                    if(idCamera==phaseTest.getIdPhase()){
                        for (ParticipantTest participantTest:phaseTest.getParticipants()){
                            if(bibNo==participantTest.getBib() && lane==participantTest.getLane()){
                                participantTest.setResult(result);
                                participantTest.setTime(time);
                                participantTest.setStatus(status);
                                System.out.println("success");
                            }
                        }
                    }
                }
            });
        }
    }
    public static void readLSTRRSLT(List<PhaseTest> phasesTestList) throws IOException {
        CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build(); // custom separator
        try (CSVReader reader = new CSVReaderBuilder(
                new FileReader("C:\\Bakalarska Práca\\Projekt folder\\csv\\LSTRRSLT.txt"))
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
    }
}
