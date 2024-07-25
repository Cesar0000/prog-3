package br.upe.operations;

import br.upe.pojos.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface ParserInterface {
    static <T> String validadeString(T str) {
        if (str == null){
            return "";
        }
        return str.toString();
    }

    static Submission parseSubmission(String rawInput){
        if(rawInput.isEmpty()) return null;

        Submission newSubmission = new Submission();
        Pattern pattern = Pattern.compile("(.*)(;)(.*)(;)(.*)(;)(.*)(;)(.*)(;)");
        Matcher matcher = pattern.matcher(rawInput);

        if (matcher.matches()) {
            if(!matcher.group(1).isEmpty()) newSubmission.setUuid(UUID.fromString(matcher.group(1)));
            if(!matcher.group(1).isEmpty()) newSubmission.setDescritor(matcher.group(3));
            if(!matcher.group(1).isEmpty()) newSubmission.setEventUuid(UUID.fromString(matcher.group(5)));
            if(!matcher.group(1).isEmpty()) newSubmission.setUserUuid(UUID.fromString(matcher.group(7)));
            if(!matcher.group(1).isEmpty()) newSubmission.setDate(Date.from(Instant.parse(matcher.group(9))));
        } else {
            return null;
        }

        return newSubmission;
    }
    static User parseUser(String rawInput){
        if(rawInput.isEmpty()) {
            return null;
        };

        Pattern pattern = Pattern.compile("(.*)(;)(.*)(;)(.*)(;)(.*)(;)(.*)(;)(.*)(;)(.*)(;)");
        Matcher matcher = pattern.matcher(rawInput);
        User newUser;

        if(matcher.matches() && matcher.group(9).equals("true")){
            newUser = new AdminUser();
            ((AdminUser)newUser).setEvents(new ArrayList<GreatEvent>());
            newUser.setSubscriptions(new ArrayList<>());
        } else {
            newUser = new CommomUser();
            newUser.setSubscriptions(new ArrayList<>());
        }

        if(matcher.matches()){
            if(!matcher.group(1).isEmpty()) newUser.setUuid(UUID.fromString(matcher.group(1)));
            if(!matcher.group(3).isEmpty()) newUser.setEmail(matcher.group(3));
            if(!matcher.group(5).isEmpty()) newUser.setPassword(matcher.group(5));
            if(!matcher.group(7).isEmpty()) newUser.setName(matcher.group(7));
            for (String subscription : matcher.group(11).split(",")){
                if(!subscription.isEmpty()) newUser.addSubscription(SubscriptionCRUD.returnSubscription(UUID.fromString(subscription)));
            }
            if(newUser instanceof AdminUser userHandler){
                for (String event : matcher.group(13).split(",")){
                    if(!event.isEmpty())userHandler.addEvent(EventCRUD.returnEvent(UUID.fromString(event)));
                }
            }
        } else {
            System.out.println("we do not get metch");
            return null;
        }
        return newUser;
    }
    static Subscription parseSubscription(String rawInput){
        if(rawInput.isEmpty()) return null;

        Subscription newSubscription = new Subscription();
        Pattern pattern = Pattern.compile("(.*)(;)(.*)(;)(.*)(;)(.*)(;)");

        Matcher matcher = pattern.matcher(rawInput);
        if(matcher.matches()){
            if(!matcher.group(1).isEmpty()) newSubscription.setUuid(UUID.fromString(matcher.group(1)));
            if(!matcher.group(3).isEmpty()) newSubscription.setSessionUuid(UUID.fromString(matcher.group(3)));
            if(!matcher.group(5).isEmpty()) newSubscription.setUserUuid(UUID.fromString(matcher.group(5)));
            if(!matcher.group(7).isEmpty()) newSubscription.setDate(Date.from(Instant.parse(matcher.group(7))));
        } else {
            return null;
        }

        return newSubscription;
    }

    static Session parseSession(String rawInput){
        if(rawInput.isEmpty()) return null;

        Session newSession = new Session();
        newSession.setSubscriptions(new ArrayList<>());

        Pattern pattern = Pattern.compile("(.*)(;)(.*)(;)(.*)(;)(.*)(;)(.*)(;)(.*)(;)");
        Matcher matcher = pattern.matcher(rawInput);

        if(matcher.matches()) {
            if(!matcher.group(1).isEmpty()) newSession.setUuid(UUID.fromString(matcher.group(1)));
            if(!matcher.group(3).isEmpty()) newSession.setEventUuid(UUID.fromString(matcher.group(3)));
            if(!matcher.group(5).isEmpty()) newSession.setDescritor(matcher.group(5));
            if(!matcher.group(7).isEmpty()) newSession.setStartDate(Date.from(Instant.parse(matcher.group(7))));
            if(!matcher.group(9).isEmpty()) newSession.setEndDate(Date.from(Instant.parse(matcher.group(9))));

            String subscriptions = matcher.group(11);

            for(String uuid : subscriptions.split(",")){
                if(!uuid.isEmpty()) newSession.addSubscription(SubscriptionCRUD.returnSubscription(UUID.fromString(uuid)));
            }
        } else {
            return null;
        }

        return newSession;
    }

    static GreatEvent parseEvent(String rawInput){
        if(rawInput.isEmpty()) {
            return null;
        }

        GreatEvent newEvent = new GreatEvent();
        newEvent.setSubmissions(new ArrayList<>());
        newEvent.setSessions(new ArrayList<>());

        Pattern pattern = Pattern.compile("(.*)(;)(.*)(;)(.*)(;)(.*)(;)(.*)(;)(.*)(;)(.*)(;)");
        Matcher matcher = pattern.matcher(rawInput);

        if(matcher.matches()) {
            if(!matcher.group(1).isEmpty()) newEvent.setUuid(UUID.fromString(matcher.group(1)));
            if(!matcher.group(3).isEmpty()) newEvent.setDescritor(matcher.group(3));
            if(!matcher.group(5).isEmpty()) newEvent.setDirector(matcher.group(5));
            if(!matcher.group(7).isEmpty()) newEvent.setStartDate(Date.from(Instant.parse(matcher.group(7))));
            if(!matcher.group(9).isEmpty()) newEvent.setEndDate(Date.from(Instant.parse(matcher.group(9))));


            String sessions = matcher.group(11);

            for(String uuid : sessions.split(",")){
                if(!uuid.isEmpty()) newEvent.addSession(SessionCRUD.returnSession(UUID.fromString(uuid)));
            }

            String submissions = matcher.group(13);
            for(String uuid : submissions.split(",")){
                if(!uuid.isEmpty()) newEvent.addSubmission(SubmissionCRUD.returnSubmission(UUID.fromString(uuid)));
            }
        } else {
            return null;
        }
        return newEvent;
    }
}
