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


        UserCRUD userCRUD = new UserCRUD();
        AdminUser sudo = createAdminUser();
        sudo.setPassword("password");
        sudo.setEmail("italan.leal@upe.br");

        AdminUser sudocpy = createAdminUser();
        sudocpy.setEmail("italanleal@gmail.com");
        sudocpy.setUuid(null);
        sudocpy.setSubscriptions(null);
        sudocpy.setEvents(null);

        sudo.checkout(sudocpy);

        System.out.println(sudo.getEmail());
    }
}
