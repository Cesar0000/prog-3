package br.upe.controllers;

/**
 * Import pojo classes
 */

import br.upe.operations.SessionCRUD;
import br.upe.operations.SubscriptionCRUD;
import br.upe.pojos.*;

/**
 * Import Factory methods
 */

import java.util.Date;
import java.util.UUID;

import static br.upe.pojos.KeeperInterface.createGreatEvent;

/**
 * Import data struct features
 */

public class TestController {
    public static void main(String[] args){
        GreatEvent even1 = createGreatEvent();
        even1.setDescritor("SUPER");

        Session session = KeeperInterface.createSession();
        session.setEventUuid(even1.getUuid());
        session.setDescritor("Mendeley Presentation");

        CommomUser user1 = KeeperInterface.createCommomUser();
        CommomUser user2 = KeeperInterface.createCommomUser();
        CommomUser user3 = KeeperInterface.createCommomUser();


        Subscription sub1 = KeeperInterface.createSubscription();
        sub1.setDate(new Date());
        sub1.setUserUuid(user1.getUuid());
        sub1.setSessionUuid(session.getUuid());

        Subscription sub2 = KeeperInterface.createSubscription();
        sub2.setDate(new Date());
        sub2.setUserUuid(user2.getUuid());
        sub2.setSessionUuid(session.getUuid());

        Subscription sub3 = KeeperInterface.createSubscription();
        sub3.setDate(new Date());
        sub3.setUserUuid(user3.getUuid());
        sub3.setSessionUuid(session.getUuid());

        session.addSubscription(sub1);
        session.addSubscription(sub2);
        session.addSubscription(sub3);

        SubscriptionCRUD subscriptionCRUD = new SubscriptionCRUD();

        subscriptionCRUD.createSubscription(sub1);
        subscriptionCRUD.createSubscription(sub2);
        subscriptionCRUD.createSubscription(sub3);

        SessionCRUD sessionCRUD = new SessionCRUD();
        sessionCRUD.createSession(session);
    }
}
