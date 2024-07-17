package br.upe.operations;

import java.io.*;

import br.upe.pojos.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserCRUD extends ClassCRUD {
    public UserCRUD(){ super(); }

    public void createUser(User user){
        try(BufferedWriter buffer = new BufferedWriter(new FileWriter(".\\state\\users.csv", true))){
            buffer.write(user.getUuid().toString() + ";");
            buffer.write( user.getEmail() + ";");
            buffer.write( Hasher.hash(user.getPassword() + ";") );
            buffer.write( user.getName() + ";");
            buffer.write(user.isAdmin() + ";");

            for (Subscription sub : user.getSubscriptions()){
                buffer.write(sub.getUuid().toString() + ",");
            }
            buffer.write(";");
            if(user instanceof AdminUser userHandler){
                for (GreatEvent event : userHandler.getEvents()){
                    buffer.write(event.getUuid().toString() + ",");
                }
            }
            buffer.write(";");

            buffer.newLine();
        } catch (Exception e) {
            System.out.println("Erro ao escrever arquivo em: " + this.getClass());
        }
    }

    public void removeUser(UUID userUUID){
        ArrayList<String> fileCopy = new ArrayList<>();

        try(BufferedReader buffer = new BufferedReader(new FileReader(".\\state\\users.csv"))){
            while(buffer.ready()){
                fileCopy.add(buffer.readLine());
            }
        } catch (Exception e) {
            System.out.println("Erro ao ler arquivo em: " + this.getClass());
        }

        try(BufferedWriter buffer = new BufferedWriter(new FileWriter(".\\state\\users.csv"))){
            for(String line: fileCopy){
                if(line.contains(userUUID.toString())) continue;
                buffer.write(line);
                buffer.newLine();
            }
        } catch (Exception e) {
            System.out.println("Erro ao escrever arquivo em: " + this.getClass());
        }
    }

    public void updateUser(UUID userUUID, User newUser){
        User user = returnUser(userUUID);
        removeUser(userUUID);
        try {
            Field[] fields = user.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.get(newUser) != null) field.set(user, field.get(newUser));
            }
        } catch (IllegalAccessException e){
            System.out.println("Erro ao atualizar em: " + this.getClass());
        }
        createUser(user);
    }

    public static User returnUser(UUID userUUID){
        String rawUser = "";

        try(BufferedReader buffer = new BufferedReader(new FileReader(".\\state\\users.csv"))){
            while(buffer.ready()){
                String line = buffer.readLine();
                if(line.contains(userUUID.toString())) {
                    rawUser  = line;
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao ler arquivo em: UserCRUD");
        }

        Pattern pattern = Pattern.compile("(.*)(;)(.*)(;)(.*)(;)(.*)(;)(.*)(;)(.*)(;)(.*)(;)");
        Matcher matcher = pattern.matcher(rawUser);
        User newUser;

        if(matcher.group(9).equals("true")){
            newUser = new AdminUser();
        } else {
            newUser = new CommomUser();
        }

        if(matcher.matches()){
            newUser.setUuid(UUID.fromString(matcher.group(1)));
            newUser.setEmail(matcher.group(3));
            newUser.setPassword(matcher.group(5));
            newUser.setName(matcher.group(7));
            for (String subscription : matcher.group(11).split(",")){
                newUser.addSubscription(SubscriptionCRUD.returnSubscription(UUID.fromString(subscription)));
            }
            if(newUser instanceof AdminUser userHandler){
                for (String event : matcher.group(13).split(",")){
                    userHandler.addEvent(EventCRUD.returnEvent(UUID.fromString(event)));
                }
            }
        }
        return newUser;
    }
}
