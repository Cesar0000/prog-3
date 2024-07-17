package br.upe.controllers;

/**
 * Import pojo classes
 */

import br.upe.operations.EventCRUD;
import br.upe.operations.SessionCRUD;
import br.upe.operations.SubscriptionCRUD;
import br.upe.operations.UserCRUD;
import br.upe.pojos.*;

/**
 * Import Factory methods
 */

import java.util.Date;
import java.util.UUID;

import static br.upe.pojos.KeeperInterface.createAdminUser;
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
        session.setStartDate(new Date());
        session.setEndDate(new Date());

        CommomUser user1 = KeeperInterface.createCommomUser();
        user1.setPassword("pass1");
        CommomUser user2 = KeeperInterface.createCommomUser();
        user2.setPassword("pass2");
        CommomUser user3 = KeeperInterface.createCommomUser();
        user3.setPassword("pass3");

        Subscription sub1 = KeeperInterface.createSubscription();
        sub1.setDate(new Date());
        sub1.setUserUuid(user1.getUuid());
        sub1.setSessionUuid(session.getUuid());
        user1.addSubscription(sub1);

        Subscription sub2 = KeeperInterface.createSubscription();
        sub2.setDate(new Date());
        sub2.setUserUuid(user2.getUuid());
        sub2.setSessionUuid(session.getUuid());
        user2.addSubscription(sub2);

        Subscription sub3 = KeeperInterface.createSubscription();
        sub3.setDate(new Date());
        sub3.setUserUuid(user3.getUuid());
        sub3.setSessionUuid(session.getUuid());
        user3.addSubscription(sub3);

        session.addSubscription(sub1);
        session.addSubscription(sub2);
        session.addSubscription(sub3);

        SubscriptionCRUD subscriptionCRUD = new SubscriptionCRUD();

        subscriptionCRUD.createSubscription(sub1);
        subscriptionCRUD.createSubscription(sub2);
        subscriptionCRUD.createSubscription(sub3);

        SessionCRUD sessionCRUD = new SessionCRUD();
        sessionCRUD.createSession(session);

        EventCRUD eventCRUD = new EventCRUD();
        eventCRUD.createEvent(even1);

        UserCRUD userCRUD = new UserCRUD();

        userCRUD.createUser(user1);
        userCRUD.createUser(user2);
        userCRUD.createUser(user3);

        AdminUser sudo = createAdminUser();
        sudo.setPassword("password");
        sudo.addEvent(even1);
        userCRUD.createUser(sudo);
    }
}
