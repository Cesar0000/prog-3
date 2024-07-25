package br.upe.operations;
import  br.upe.pojos.*;

import java.io.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EventCRUD extends BaseCRUD {
    public EventCRUD(){ super(); }

    public void createEvent(GreatEvent event){
        try(BufferedWriter buffer = new BufferedWriter(new FileWriter(".\\state\\events.csv", true))){
            buffer.write(event.getUuid().toString() + ";");
            buffer.write( event.getDescritor() + ";");
            buffer.write( event.getDirector() + ";");
            buffer.write( event.getStartDate().toInstant().toString() + ";");
            buffer.write( event.getEndDate().toInstant().toString() + ";");
            for (Session session : event.getSessions()){
                buffer.write(session.getUuid().toString() + ",");
            }
            buffer.write(";");
            for (Submission submission : event.getSubmissions()){
                buffer.write(submission.getUuid().toString() + ",");
            }
            buffer.write(";");

            buffer.newLine();
        } catch (Exception e) {}
    }
    public void deleteEvent(UUID eventUuid){
        ArrayList<String> fileCopy = new ArrayList<>();
        try(BufferedReader buffer = new BufferedReader(new FileReader(".\\state\\events.csv"))){
            while(buffer.ready()){
                fileCopy.add(buffer.readLine());
            };
        } catch (Exception e) {}

        try(BufferedWriter buffer = new BufferedWriter(new FileWriter(".\\state\\events.csv"))){
            for(String line: fileCopy){
                if(line.contains(eventUuid.toString())) continue;
                buffer.write(line);
                buffer.newLine();
            }
        } catch (Exception e) {}
    }

    public void updateEvent(UUID eventUuid, GreatEvent source) {

        GreatEvent event = returnEvent(eventUuid);
        deleteEvent(eventUuid);
        HelperInterface.checkout(source, event);
        createEvent(event);
    }

    public static GreatEvent returnEvent(UUID eventUuid){
        try(BufferedReader buffer = new BufferedReader(new FileReader(".\\state\\events.csv"))){
            while(buffer.ready()){
                String line = buffer.readLine();
                if(line.contains(eventUuid.toString())) {
                    return ParserInterface.parseEvent(line);
                }

            }
        } catch (Exception e) {}

        return null;
    }
    public static Collection<GreatEvent> returnEvent(){
        Collection<GreatEvent> events = new ArrayList<>();
        try(BufferedReader buffer = new BufferedReader(new FileReader(".\\state\\events.csv"))){
            while(buffer.ready()){
                String line = buffer.readLine();
                if(line.isEmpty()) {
                    events.add(ParserInterface.parseEvent(line));

                }
            }
        } catch (Exception e) {}

        return events;
    }
}
