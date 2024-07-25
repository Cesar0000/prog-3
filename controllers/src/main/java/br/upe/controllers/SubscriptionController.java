package br.upe.controllers;

import br.upe.pojos.*;

import java.util.UUID;

public class SubscriptionController {
    private CRUDController crudController;
    private StateController stateController;

    public SubscriptionController(StateController stateController, CRUDController crudController) {
        this.stateController = stateController;
        this.crudController = crudController;
    }

    public void removeSubscription(UUID subscriptionUuid){
        Subscription newSubscription = crudController.subscriptionCRUD.returnSubscription(subscriptionUuid);
        User newUser = crudController.userCRUD.returnUser(newSubscription.getUserUuid());
        Session newSession = crudController.sessionCRUD.returnSession(newSubscription.getSessionUuid());

        for(Subscription subscription : newUser.getSubscriptions()){
            if(subscription.getUuid().equals(subscriptionUuid)){
                newUser.getSubscriptions().remove(subscription);
            }
        }

        for(Subscription subscription : newSession.getSubscriptions()){
            if(subscription.getUuid().equals(subscriptionUuid)){
                newSession.getSubscriptions().remove(subscription);
            }
        }

        Session sessionHandler = KeeperInterface.createSession();
        User userHandler;
        if(newUser instanceof AdminUser){
            userHandler = KeeperInterface.createAdminUser();
        } else {
            userHandler = KeeperInterface.createCommomUser();
        }

        userHandler.setSubscriptions(newUser.getSubscriptions());
        sessionHandler.setSubscriptions(newSession.getSubscriptions());

        crudController.subscriptionCRUD.deleteSubscription(subscriptionUuid);

        crudController.userCRUD.updateUser(newUser.getUuid(), userHandler);
        crudController.sessionCRUD.updateSession(newSession.getUuid(), sessionHandler);

    }

}
