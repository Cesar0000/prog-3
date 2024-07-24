package br.upe.operations;

import br.upe.pojos.HelperInterface;
import br.upe.pojos.Session;
import br.upe.pojos.Subscription;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class SessionCRUD extends BaseCRUD {
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
    public void deleteSession(UUID sessionUuid){
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

    public void updateSession(UUID sessionUuid, Session source) {
        Session session = returnSession(sessionUuid);
        deleteSession(sessionUuid);
        HelperInterface.checkout(source, session);
        createSession(session);
    }

    public static Session returnSession(UUID sessionUuid){
        String rawSession = "";

        try(BufferedReader buffer = new BufferedReader(new FileReader(".\\state\\sessions.csv"))){
            while(buffer.ready()){
                String line = buffer.readLine();
                if(line.contains(sessionUuid.toString())) {
                    return ParserInterface.parseSession(line);
                }
            };
        } catch (Exception e) {}

        return null;
    }
    public static Collection<Session> returnSession(){
        Collection<Session> sessions = new ArrayList<>();
        try(BufferedReader buffer = new BufferedReader(new FileReader(".\\state\\sessions.csv"))){
            while(buffer.ready()){
                String line = buffer.readLine();
                if(!line.isEmpty()) {
                    sessions.add(ParserInterface.parseSession(line));
                }
            };
        } catch (Exception e) {}

        return sessions;
    }
}
