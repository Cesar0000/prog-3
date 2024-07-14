package br.upe.operations;

import br.upe.pojos.Session;
import br.upe.pojos.Subscription;

import java.io.*;
import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SessionMirror {
    public SessionMirror(){
        try {
            File d = new File(".\\state");
            while(!d.exists()) d.mkdirs();
        } catch (Exception e) {}
    }

    public void createSession(Session session){
        try(BufferedWriter buffer = new BufferedWriter(new FileWriter(".\\state\\sessions.csv", true))){
            buffer.write(session.getUuid().toString() + ";");
            buffer.write( session.getEventUuid().toString() + ";");
            for (Subscription sub : session.getSubscriptions()){
                buffer.write(sub.getUuid().toString() + ",");
            }
            buffer.write(";");

            buffer.newLine();
        } catch (Exception e) {}
    }
    public void removeSession(UUID sessionUuid){
        int i = 0;
        ArrayList<String> filecopy = new ArrayList<>();

        try(BufferedReader buffer = new BufferedReader(new FileReader(".\\state\\sessions.csv"))){
            while(buffer.ready()){
                filecopy.add(buffer.readLine());
            };
        } catch (Exception e) {}

        try(BufferedWriter buffer = new BufferedWriter(new FileWriter(".\\state\\sessions.csv"))){
            for(String line: filecopy){
                if(line.contains(sessionUuid.toString())) continue;
                buffer.write(line);
                buffer.newLine();
            }
        } catch (Exception e) {}
    }
    public void updateSession(Session session){
        removeSession(session.getUuid());
        createSession(session);
    }
    public Session getSession(UUID sessionUuid){
        String rawSession = "";

        try(BufferedReader buffer = new BufferedReader(new FileReader(".\\state\\sessions.csv"))){
            while(buffer.ready()){
                String line = buffer.readLine();
                if(line.contains(sessionUuid.toString())) {
                    rawSession = line;
                }
            };
        } catch (Exception e) {}

        Session newSession = new Session();
        newSession.setSubscriptions(new ArrayList<>());

        Pattern pattern = Pattern.compile("(.*)(;)(.*)(;)(.*)(;)");
        Matcher matcher = pattern.matcher(rawSession);

        if(matcher.matches()) {
            newSession.setUuid(UUID.fromString(matcher.group(1)));
            newSession.setEventUuid(UUID.fromString(matcher.group(3)));

            String subs = matcher.group(5);
            for(String sub : subs.split(",")){
                System.out.println(sub);
            }
        }

        return newSession;
    }
}
