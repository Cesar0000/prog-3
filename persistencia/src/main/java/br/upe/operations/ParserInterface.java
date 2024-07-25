package br.upe.operations;

import br.upe.pojos.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface ParserInterface {
    public static Submission parseSubmission(String rawInput){
        if(rawInput.isEmpty()) return null;

        Submission newSubmission = new Submission();
        Pattern pattern = Pattern.compile("(.*)(;)(.*)(;)(.*)(;)(.*)(;)");
        Matcher matcher = pattern.matcher(rawInput);

        if (matcher.matches()) {
            newSubmission.setUuid(UUID.fromString(matcher.group(1)));
            newSubmission.setEventUuid(UUID.fromString(matcher.group(3)));
            newSubmission.setUserUuid(UUID.fromString(matcher.group(5)));
            newSubmission.setDate(Date.from(Instant.parse(matcher.group(7))));
        } else {
            return null;
        }

        return newSubmission;
    }
    public static User parseUser(String rawInput){
        if(rawInput.isEmpty()) return null;

        Pattern pattern = Pattern.compile("(.*)(;)(.*)(;)(.*)(;)(.*)(;)(.*)(;)(.*)(;)(.*)(;)");
        Matcher matcher = pattern.matcher(rawInput);
        User newUser;

        if(matcher.matches() && matcher.group(9).equals("true")){
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
                if(!subscription.isEmpty()) newUser.addSubscription(SubscriptionCRUD.returnSubscription(UUID.fromString(subscription)));
            }
            if(newUser instanceof AdminUser userHandler){
                for (String event : matcher.group(13).split(",")){
                    if(!event.isEmpty())userHandler.addEvent(EventCRUD.returnEvent(UUID.fromString(event)));
                }
            }
        } else {
            return null;
        }
        return newUser;
    }
    public static Subscription parseSubscription(String rawInput){
        if(rawInput.isEmpty()) return null;

        Subscription newSubscription = new Subscription();
        Pattern pattern = Pattern.compile("(.*)(;)(.*)(;)(.*)(;)(.*)(;)");

        Matcher matcher = pattern.matcher(rawInput);
        if(matcher.matches()){
            newSubscription.setUuid(UUID.fromString(matcher.group(1)));
            newSubscription.setSessionUuid(UUID.fromString(matcher.group(3)));
            newSubscription.setUserUuid(UUID.fromString(matcher.group(5)));
            newSubscription.setDate(Date.from(Instant.parse(matcher.group(7))));
        } else {
            return null;
        }

        return newSubscription;
    }

    public static Session parseSession(String rawInput){
        if(rawInput.isEmpty()) return null;

        Session newSession = new Session();
        newSession.setSubscriptions(new ArrayList<>());

        Pattern pattern = Pattern.compile("(.*)(;)(.*)(;)(.*)(;)(.*)(;)(.*)(;)(.*)(;)");
        Matcher matcher = pattern.matcher(rawInput);

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
        } else {
            return null;
        }

        return newSession;
    }

    public static GreatEvent parseEvent(String rawInput){
        if(rawInput.isEmpty()) return null;

        GreatEvent newEvent = new GreatEvent();
        newEvent.setSubmissions(new ArrayList<>());
        newEvent.setSessions(new ArrayList<>());

        Pattern pattern = Pattern.compile("(.*)(;)(.*)(;)(.*)(;)(.*)(;)(.*)(;)(.*)(;)(.*)(;)");
        Matcher matcher = pattern.matcher(rawInput);

        if(matcher.matches()) {
            newEvent.setUuid(UUID.fromString(matcher.group(1)));
            newEvent.setDescritor(matcher.group(3));
            newEvent.setDirector(matcher.group(5));
            newEvent.setStartDate(Date.from(Instant.parse(matcher.group(7))));
            newEvent.setEndDate(Date.from(Instant.parse(matcher.group(9))));


            String sessions = matcher.group(11);

            for(String uuid : sessions.split(",")){
                if(!uuid.isEmpty()) newEvent.addSession(SessionCRUD.returnSession(UUID.fromString(uuid)));
            }

            String submissions = matcher.group(11);

            for(String uuid : submissions.split(",")){
                if(!uuid.isEmpty()) newEvent.addSubmission(SubmissionCRUD.returnSubmission(UUID.fromString(uuid)));
            }
        } else {
            return null;
        }
        return newEvent;
    }
}
