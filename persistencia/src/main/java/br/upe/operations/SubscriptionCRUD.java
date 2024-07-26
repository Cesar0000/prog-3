package br.upe.operations;

import br.upe.pojos.HelperInterface;
import br.upe.pojos.Subscription;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class SubscriptionCRUD extends BaseCRUD {
    public SubscriptionCRUD(){ super(); }

    public void createSubscription(Subscription subscription){
        try(BufferedWriter buffer = new BufferedWriter(new FileWriter(".\\state\\subscriptions.csv", true))){
            buffer.write(ParserInterface.validadeString(subscription.getUuid()) + ";");
            buffer.write(ParserInterface.validadeString(subscription.getSessionUuid()) + ";");
            buffer.write(ParserInterface.validadeString(subscription.getUserUuid()) + ";");
            buffer.write((subscription.getDate() != null? ParserInterface.validadeString(subscription.getDate().toInstant().toString()): "") + ";");

            buffer.newLine();
        } catch (Exception e) {}
    }

    public void deleteSubscription(UUID subscriptionUuid){
        ArrayList<String> fileCopy = new ArrayList<>();

        try(BufferedReader buffer = new BufferedReader(new FileReader(".\\state\\subscriptions.csv"))){
            while(buffer.ready()){
                fileCopy.add(buffer.readLine());
            }
        } catch (Exception e) {}

        try(BufferedWriter buffer = new BufferedWriter(new FileWriter(".\\state\\subscriptions.csv"))){
            for(String line: fileCopy){
                if(line.contains(subscriptionUuid.toString())) continue;
                buffer.write(line);
                buffer.newLine();
            }
        } catch (Exception e) {}
    }

    public void updateSubscription(UUID subscriptionUuid, Subscription source){
        Subscription subscription = returnSubscription(subscriptionUuid);
        deleteSubscription(subscriptionUuid);
        HelperInterface.checkout(source, subscription);
        createSubscription(subscription);
    }

    public static Subscription returnSubscription(UUID subscriptionUuid){
        try(BufferedReader buffer = new BufferedReader(new FileReader(".\\state\\subscriptions.csv"))){
            while(buffer.ready()){
                String line = buffer.readLine();
                if(line.contains(subscriptionUuid.toString())) {
                    return ParserInterface.parseSubscription(line);
                }
            }
        } catch (Exception e) {}
        return null;
    }

    public static Collection<Subscription> returnSubscription(){
        Collection<Subscription> subscriptions = new ArrayList<>();

        try(BufferedReader buffer = new BufferedReader(new FileReader(".\\state\\subscriptions.csv"))){
            while(buffer.ready()){
                String line = buffer.readLine();
                if(!line.isEmpty()){
                    subscriptions.add(ParserInterface.parseSubscription(line));
                }
            }
        } catch (Exception e) {}

        return subscriptions;
    }
}