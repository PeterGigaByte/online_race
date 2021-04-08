package fei.stuba.bp.rigo.preteky;

import com.opencsv.*;
import fei.stuba.bp.rigo.preteky.models.testModels.ParticipantTest;
import fei.stuba.bp.rigo.preteky.models.testModels.PhaseTest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class PretekyApplication {

    public static void main(String[] args) {
        SpringApplication.run(PretekyApplication.class, args);

        List<PhaseTest> phasesTestList = createSimulationParameters();


        readLSTRslt(phasesTestList);
        readLSTRRSLT(phasesTestList);

        phasesTestList.forEach(x -> {
            x.getParticipants().forEach(y -> System.out.println(y.toString()));
            System.out.println(x.getNameOfPhase()+"; "+x.getWind()+"; "+x.getStatus()+"\n");
        });
    }

    // klasický import / export / ** / ** / ** / ** / ** / ** / ** / ** / ** / ** / ** / ** / ** / ** / ** / ** / ** / **


    public static List<PhaseTest> createSimulationParameters(){
        String[] firstNames = {"Peter","Jano","Miro","Marián","Stano","Patrik","Milan","Jozef"};
        String[] lastNames = {"Ščasný","Maječka","Vajenský","Trajenský","Lombardský","Parížsky","Šalátový","Majonézový"};
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
    public static void readLSTRslt(List<PhaseTest> phasesTestList) {
        try{
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
        }catch (Exception e){
            System.out.println("function readLSTRslt threw exception: "+"'"+e.getMessage()+"'");
        }
    }
    public static void readLSTRRSLT(List<PhaseTest> phasesTestList) {
        try{
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
        } catch (Exception e) {
            System.out.println("function readLSTRRSLT threw exception: "+"'"+e.getMessage()+"'");
        }
    }

    // kominikácia cez socket / ** / ** / ** / ** / ** / ** / ** / ** / ** / ** / ** / ** / ** / ** / ** / ** / ** / **

    public static void readingFromSocket(int port) throws IOException {
        ServerSocket server = new ServerSocket(port);
        Socket socket = server.accept();
        DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        readingBinaryDataFromSocket(in);

        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
    }
    public static void readingBinaryDataFromSocket(DataInputStream in) throws IOException {
        char dataType = in.readChar();
        int length = in.readInt();
        if(dataType == 's') {
            byte[] messageByte = new byte[length];
            boolean end = false;
            StringBuilder dataString = new StringBuilder(length);
            int totalBytesRead = 0;
            while(!end) {
                int currentBytesRead = in.read(messageByte);
                totalBytesRead = currentBytesRead + totalBytesRead;
                if(totalBytesRead <= length) {
                    dataString
                            .append(new String(messageByte, 0, currentBytesRead, StandardCharsets.UTF_8));
                } else {
                    dataString
                            .append(new String(messageByte, 0, length - totalBytesRead + currentBytesRead,
                                    StandardCharsets.UTF_8));
                }
                if(dataString.length()>=length) {
                    end = true;
                }
            }
        }
    }
    public static void writingDataToSocket(DataOutputStream out) throws IOException {
        char type = 's'; // s for string
        String data = "This is a string of length 29";
        byte[] dataInBytes = data.getBytes(StandardCharsets.UTF_8);

        out.writeChar(type);
        out.writeInt(dataInBytes.length);
        out.write(dataInBytes);
    }

}
