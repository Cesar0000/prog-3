package br.upe.operations;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryState {
    public static UUID userFromEmail(String email) {
        String rawUser = "";

        try(BufferedReader buffer = new BufferedReader(new FileReader(".\\state\\users.csv"))){
            while(buffer.ready()){
                String line = buffer.readLine();
                if(line.contains(email)) {
                    rawUser  = line;
                    break;
                }
            }
        } catch (Exception e) {}

        if(rawUser.isEmpty()) return null;

        Pattern pattern = Pattern.compile("(.*)(;)(.*)(;)(.*)(;)(.*)(;)(.*)(;)(.*)(;)(.*)(;)");
        Matcher matcher = pattern.matcher(rawUser);

        if(matcher.matches()){
            return UUID.fromString(matcher.group(1));
        } return null;
    }
}
