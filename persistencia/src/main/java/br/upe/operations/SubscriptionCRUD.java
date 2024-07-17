package br.upe.operations;

import br.upe.pojos.Session;
import br.upe.pojos.Subscription;

import java.io.*;
import java.lang.reflect.Field;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SubscriptionCRUD extends ClassCRUD{
    public SubscriptionCRUD(){ super(); }

    public void createSubscription(Subscription subscription){
        try(BufferedWriter buffer = new BufferedWriter(new FileWriter(".\\state\\subscriptions.csv", true))){
            buffer.write(subscription.getUuid().toString() + ";");
            buffer.write( subscription.getSessionUuid().toString() + ";");
            buffer.write( subscription.getUserUuid().toString() + ";");
            buffer.write( subscription.getDate().toInstant().toString() + ";");

            buffer.newLine();
        } catch (Exception e) {}
    }
    public void removeSubscription(UUID subscriptionUuid){
        ArrayList<String> fileCopy = new ArrayList<>();

        try(BufferedReader buffer = new BufferedReader(new FileReader(".\\state\\subscriptions.csv"))){
            while(buffer.ready()){
                fileCopy.add(buffer.readLine());
            };
        } catch (Exception e) {}

        try(BufferedWriter buffer = new BufferedWriter(new FileWriter(".\\state\\subscriptions.csv"))){
            for(String line: fileCopy){
                if(line.contains(subscriptionUuid.toString())) continue;
                buffer.write(line);
                buffer.newLine();
            }
        } catch (Exception e) {}
    }

    public void updateSubscription(UUID subscriptionUuid, Subscription newSubscription) throws IllegalAccessException {
        Subscription subscription = returnSubscription(subscriptionUuid);
        removeSubscription(subscriptionUuid);
        try {
            Field[] fields = subscription.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.get(newSubscription) != null) field.set(subscription, field.get(newSubscription));
            }
        } catch (IllegalAccessException e){
            System.out.println("Erro ao atualizar em: " + this.getClass());
        }
        createSubscription(subscription);
    }

    public static Subscription returnSubscription(UUID subscriptionUuid){
        String rawSubscription = "";

        try(BufferedReader buffer = new BufferedReader(new FileReader(".\\state\\subscriptions.csv"))){
            while(buffer.ready()){
                String line = buffer.readLine();
                if(line.contains(subscriptionUuid.toString())) {
                    rawSubscription = line;
                    break;
                }
            };
        } catch (Exception e) {}

        Subscription newSubscription = new Subscription();
        Pattern pattern = Pattern.compile("(.*)(;)(.*)(;)(.*)(;)(.*)(;)");

        Matcher matcher = pattern.matcher(rawSubscription);
        if(matcher.matches()){
            newSubscription.setUuid(UUID.fromString(matcher.group(1)));
            newSubscription.setSessionUuid(UUID.fromString(matcher.group(3)));
            newSubscription.setUserUuid(UUID.fromString(matcher.group(5)));
            newSubscription.setDate(Date.from(Instant.parse(matcher.group(7))));
        }

        return newSubscription;
    }
}