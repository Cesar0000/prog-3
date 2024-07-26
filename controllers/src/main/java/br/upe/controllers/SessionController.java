package br.upe.controllers;

import br.upe.pojos.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class SessionController {

    public SessionController(StateController stateController, CRUDController crudController) {
        this.stateController = stateController;
        this.crudController = crudController;
    }
    private final StateController stateController;
    private final CRUDController crudController;

    public boolean createNewSession(String descritor){
        if(stateController.getCurrentUser() instanceof AdminUser){

            Session session = KeeperInterface.createSession();
            session.setUuid(UUID.randomUUID());
            session.setDescritor(descritor);
            session.setEventUuid(stateController.getCurrentEvent().getUuid());
            session.setSubscriptions(new ArrayList<>());

            stateController.setCurrentSession(session);
            stateController.getCurrentEvent().addSession(session);

            GreatEvent eventHandler = KeeperInterface.createGreatEvent();
            eventHandler.setSessions(stateController.getCurrentEvent().getSessions());

            crudController.sessionCRUD.createSession(session);
            crudController.eventCRUD.updateEvent(stateController.getCurrentEvent().getUuid(), eventHandler);

            return true;
        }
        return false;
    }
    public void updateSessionDescritor(String descritor){
        Session source = KeeperInterface.createSession();
        source.setDescritor(descritor);
        crudController.sessionCRUD.updateSession(stateController.getCurrentSession().getUuid(), source);
    }
    public boolean updateSessionStartDate(Date startDate){
        Session source = KeeperInterface.createSession();
        source.setStartDate(startDate);
        if(startDate.after(stateController.getCurrentEvent().getStartDate())){
            crudController.sessionCRUD.updateSession(stateController.getCurrentSession().getUuid(), source);
            return true;
        } return false;
    }
    public boolean updateSessionEndDate(Date endDate){
        Session source = KeeperInterface.createSession();
        source.setEndDate(endDate);
        if(endDate.before(stateController.getCurrentEvent().getEndDate())){
            crudController.sessionCRUD.updateSession(stateController.getCurrentSession().getUuid(), source);
            return true;
        } return false;
    }
    public void addSessionsSubscription(){
        Subscription subscription = KeeperInterface.createSubscription();
        subscription.setUuid(UUID.randomUUID());
        subscription.setSessionUuid(stateController.getCurrentSession().getUuid());
        subscription.setUserUuid(stateController.getCurrentUser().getUuid());
        subscription.setDate(new Date());

        stateController.getCurrentSession().getSubscriptions().add(subscription);
        stateController.getCurrentUser().getSubscriptions().add(subscription);

        User userHandler;
        if(stateController.getCurrentUser() instanceof AdminUser){
            userHandler = KeeperInterface.createAdminUser();
        } else {
            userHandler = KeeperInterface.createCommomUser();
        } userHandler.setSubscriptions(stateController.getCurrentUser().getSubscriptions());

        Session sessionHandler = KeeperInterface.createSession();
        sessionHandler.setSubscriptions(stateController.getCurrentSession().getSubscriptions());

        crudController.subscriptionCRUD.createSubscription(subscription);
        crudController.sessionCRUD.updateSession(stateController.getCurrentSession().getUuid(), sessionHandler);
        crudController.userCRUD.updateUser(stateController.getCurrentUser().getUuid(), userHandler);
    }
    public void changeCurrentSession(UUID sessionUuid){
        stateController.setCurrentSession(crudController.sessionCRUD.returnSession(sessionUuid));
    }
    public void closeCurrentSession(){
        stateController.setCurrentSession(null);
    }
}
