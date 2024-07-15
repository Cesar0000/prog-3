package br.upe.operations;

import br.upe.pojos.Session;
import br.upe.pojos.Subscription;

import java.io.*;
import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SessionCRUD {
    public SessionCRUD(){
        try {
            File d = new File(".\\state");
            while(!d.exists()) d.mkdirs();
        } catch (Exception e) {}
    }

    public void createSession(Session session){
        try(BufferedWriter buffer = new BufferedWriter(new FileWriter(".\\state\\sessions.csv", true))){
            buffer.write(session.getUuid().toString() + ";");
            buffer.write( session.getEventUuid().toString() + ";");
            buffer.write( session.getDescritor() + ";");
            for (Subscription sub : session.getSubscriptions()){
                buffer.write(sub.getUuid().toString() + ",");
            }
            buffer.write(";");

            buffer.newLine();
        } catch (Exception e) {}
    }
    public void removeSession(UUID sessionUuid){
        ArrayList<String> fileCopy = new ArrayList<>();

        try(BufferedReader buffer = new BufferedReader(new FileReader(".\\state\\sessions.csv"))){
            while(buffer.ready()){
                fileCopy.add(buffer.readLine());
            };
        } catch (Exception e) {}

        try(BufferedWriter buffer = new BufferedWriter(new FileWriter(".\\state\\sessions.csv"))){
            for(String line: fileCopy){
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
    public static Session returnSession(UUID sessionUuid){
        String rawSession = "";

        try(BufferedReader buffer = new BufferedReader(new FileReader(".\\state\\sessions.csv"))){
            while(buffer.ready()){
                String line = buffer.readLine();
                if(line.contains(sessionUuid.toString())) {
                    rawSession = line;
                    break;
                }
            };
        } catch (Exception e) {}

        Session newSession = new Session();
        newSession.setSubscriptions(new ArrayList<>());

        Pattern pattern = Pattern.compile("(.*)(;)(.*)(;)(.*)(;)(.*)(;)");
        Matcher matcher = pattern.matcher(rawSession);

        if(matcher.matches()) {
            newSession.setUuid(UUID.fromString(matcher.group(1)));
            newSession.setEventUuid(UUID.fromString(matcher.group(3)));
            newSession.setDescritor(matcher.group(5));

            String subscriptions = matcher.group(7);

            for(String uuid : subscriptions.split(",")){
                newSession.addSubscription(SubscriptionCRUD.returnSubscription(UUID.fromString(uuid)));
            }
        }

        return newSession;
    }
}
