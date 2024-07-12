package br.upe.controllers;

import br.upe.pojos.*;

import java.util.ArrayList;

public class TestController {
    public static void main(String[] args){

        Keeper keeper = new Keeper();
        GreatEvent even1 = keeper.createGreatEvent();

        even1.setName("SUPER");
        even1.setSubEvents( new ArrayList<PlainEvent>() );

        SubEvent HansenAI = keeper.createSubEvent();
        HansenAI.setName("HansenAI2");

        even1.getSubEvents().add( HansenAI );

        CommomUser user1 = keeper.createCommomUser();
        user1.setName("Carlos");
        user1.setEmail("carlos@gmail.com");
        user1.setPassword("123456");

        Subscription sub1 = keeper.createSubscription(user1);

        user1.setSubscriptions(sub1);
        System.out.println(user1.getSubscriptions().iterator().next().getValidationCode());
        System.out.println(even1.getSubEvents().iterator().next().getName());
    }
}
