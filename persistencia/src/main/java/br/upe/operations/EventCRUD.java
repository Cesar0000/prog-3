package br.upe.operations;
import  br.upe.pojos.*;
import org.w3c.dom.events.Event;

import java.io.*;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EventCRUD extends ClassCRUD {
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
    public void removeEvent(UUID eventUuid){
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
    public void updateEvent(UUID eventUuid, Event newEvent) throws IllegalAccessException {
        GreatEvent event =  returnEvent(eventUuid);
        removeEvent(eventUuid);
        try {
            Field[] fields = event.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.get(newEvent) != null) field.set(event, field.get(newEvent));
            }
        } catch (IllegalAccessException e){
            System.out.println("Erro ao atualizar em: " + this.getClass());
        }
        createEvent(event);
    }
    public static GreatEvent returnEvent(UUID eventUuid){
        String rawEvent = "";

        try(BufferedReader buffer = new BufferedReader(new FileReader(".\\state\\events.csv"))){
            while(buffer.ready()){
                String line = buffer.readLine();
                if(line.contains(eventUuid.toString())) {
                    rawEvent = line;
                    break;
                }
            };
        } catch (Exception e) {}

        GreatEvent newEvent = new GreatEvent();
        newEvent.setSubmissions(new ArrayList<>());
        newEvent.setSessions(new ArrayList<>());

        Pattern pattern = Pattern.compile("(.*)(;)(.*)(;)(.*)(;)(.*)(;)(.*)(;)(.*)(;)(.*)(;)");
        Matcher matcher = pattern.matcher(rawEvent);

        if(matcher.matches()) {
            newEvent.setUuid(UUID.fromString(matcher.group(1)));
            newEvent.setDescritor(matcher.group(3));
            newEvent.setDirector(matcher.group(5));
            newEvent.setStartDate(Date.from(Instant.parse(matcher.group(7))));
            newEvent.setEndDate(Date.from(Instant.parse(matcher.group(9))));


            String sessions = matcher.group(11);

            for(String uuid : sessions.split(",")){
                newEvent.addSession(SessionCRUD.returnSession(UUID.fromString(uuid)));
            }

            String submissions = matcher.group(11);

            for(String uuid : submissions.split(",")){
                newEvent.addSubmission(SubmissionCRUD.returnSubmission(UUID.fromString(uuid)));
            }
        }

        return newEvent;
    }

}
