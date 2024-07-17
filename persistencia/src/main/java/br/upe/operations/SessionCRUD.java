package br.upe.operations;

import br.upe.pojos.Session;
import br.upe.pojos.Subscription;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.reflect.Field;

public class SessionCRUD extends ClassCRUD {
    public SessionCRUD(){ super(); }

    public void createSession(Session session){
        try(BufferedWriter buffer = new BufferedWriter(new FileWriter(".\\state\\sessions.csv", true))){
            buffer.write(session.getUuid().toString() + ";");
            buffer.write( session.getEventUuid().toString() + ";");
            buffer.write( session.getDescritor() + ";");
            buffer.write( session.getStartDate().toInstant().toString() + ";");
            buffer.write( session.getEndDate().toInstant().toString() + ";");
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

    public void updateSession(UUID sessionUuid, Session newSession) throws IllegalAccessException {
        Session session = returnSession(sessionUuid);
        removeSession(sessionUuid);
        try {
            Field[] fields = session.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.get(newSession) != null) field.set(session, field.get(newSession));
            }
        } catch (IllegalAccessException e){
            System.out.println("Erro ao atualizar em: " + this.getClass());
        }
        createSession(session);
    }

    public void updateSession(UUID sessionUUID, Date newStartDate, Date newEndDate){
        Session session = returnSession(sessionUUID);
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

        Pattern pattern = Pattern.compile("(.*)(;)(.*)(;)(.*)(;)(.*)(;)(.*)(;)(.*)(;)");
        Matcher matcher = pattern.matcher(rawSession);

        if(matcher.matches()) {
            newSession.setUuid(UUID.fromString(matcher.group(1)));
            newSession.setEventUuid(UUID.fromString(matcher.group(3)));
            newSession.setDescritor(matcher.group(5));
            newSession.setStartDate(Date.from(Instant.parse(matcher.group(7))));
            newSession.setEndDate(Date.from(Instant.parse(matcher.group(9))));


            String subscriptions = matcher.group(11);

            for(String uuid : subscriptions.split(",")){
                newSession.addSubscription(SubscriptionCRUD.returnSubscription(UUID.fromString(uuid)));
            }
        }

        return newSession;
    }
}
