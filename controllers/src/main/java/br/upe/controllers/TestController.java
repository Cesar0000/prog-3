package br.upe.controllers;

/**
 * Import pojo classes
 */

import br.upe.pojos.CommomUser;
import br.upe.pojos.GreatEvent;
import br.upe.pojos.Subscription;

/**
 * Import Factory methods
 */

import static br.upe.pojos.KeeperInterface.createCommomUser;
import static br.upe.pojos.KeeperInterface.createGreatEvent;
import static br.upe.pojos.KeeperInterface.createSubscription;

/**
 * Import data struct features
 */

public class TestController {
    public static void main(String[] args){

        GreatEvent even1 = createGreatEvent();
        even1.setName("SUPER");

    }
    //vai commit
}
