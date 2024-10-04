package br.upe.operations;

import java.io.*;

import br.upe.pojos.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class UserCRUD extends BaseCRUD {
    public UserCRUD(){ super(); }

    public void createUser(User user){
        try(BufferedWriter buffer = new BufferedWriter(new FileWriter(".\\state\\users.csv", true))){
            buffer.write(ParserInterface.validadeString(user.getUuid()) + ";");
            buffer.write(ParserInterface.validadeString(user.getEmail()) + ";");
            buffer.write(ParserInterface.validadeString(user.getPassword()) + ";");
            buffer.write(ParserInterface.validadeString(user.getName()) + ";");
            buffer.write(ParserInterface.validadeString(user.isAdmin()) + ";");

            for (Subscription sub : user.getSubscriptions()){
                buffer.write(ParserInterface.validadeString(sub.getUuid()) + ",");
            }
            buffer.write(";");
            if (user instanceof AdminUser userHandler){
                for (GreatEvent event : userHandler.getEvents()){
                    buffer.write(ParserInterface.validadeString(event.getUuid()) + ",");
                }
            }

            buffer.write(";");

            buffer.newLine();
        } catch (Exception e) {
            System.out.println("Erro ao escrever arquivo em: " + this.getClass());
        }
    }

    public void deleteUser(UUID userUUID){
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

    public void updateUser(UUID userUUID, User source){
        User user = returnUser(userUUID);
        deleteUser(userUUID);
        HelperInterface.checkout(source, user);
        createUser(user);
    }

    public static User returnUser(UUID userUUID){
        try(BufferedReader buffer = new BufferedReader(new FileReader(".\\state\\users.csv"))){
            while(buffer.ready()){
                String line = buffer.readLine();
                if(line.contains(userUUID.toString())) {
                    return ParserInterface.parseUser(line);
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao ler arquivo em: UserCRUD");
        }

        return null;
    }

    public Collection<User> returnUser(){
        Collection<User> users = new ArrayList<>();

        try(BufferedReader buffer = new BufferedReader(new FileReader(".\\state\\users.csv"))){
            while(buffer.ready()){
                String line = buffer.readLine();
                if(!line.isEmpty()){
                    users.add(ParserInterface.parseUser(line));
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao ler arquivo em: UserCRUD");
        }

        return users;
    }
}
