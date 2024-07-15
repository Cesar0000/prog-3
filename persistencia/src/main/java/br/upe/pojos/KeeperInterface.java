package br.upe.pojos;

import java.util.ArrayList;
import java.util.UUID;

public interface KeeperInterface {
    static GreatEvent createGreatEvent(){
        GreatEvent event =  new GreatEvent();
        event.setUuid(UUID.randomUUID());
        event.setSessions(new ArrayList<>());
        event.setSubmissions(new ArrayList<>());
        return event;
    }
    static CommomUser createCommomUser() {
        CommomUser commomUser = new CommomUser();
        commomUser.setUuid(UUID.randomUUID());
        commomUser.setSubscriptions(new ArrayList<Subscription>());
        return commomUser;
    }
    static Session createSession() {
        Session session = new Session();
        session.setUuid(UUID.randomUUID());
        session.setSubscriptions(new ArrayList<>());
        return session;
    }
    static Subscription createSubscription(){
        Subscription subscription = new Subscription();
        subscription.setUuid(UUID.randomUUID());
        return subscription;
    }
}
