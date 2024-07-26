package br.upe.controllers;

import br.upe.pojos.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

public class EventController {
    public EventController(StateController stateController, CRUDController crudController){
        this.stateController = stateController;
        this.crudController = crudController;
    }
    private final StateController stateController;
    private final CRUDController crudController;

    public boolean createNewEvent(String descritor, String director){
        if(stateController.getCurrentUser() instanceof AdminUser user){
            GreatEvent event = KeeperInterface.createGreatEvent();
            event.setUuid(UUID.randomUUID());
            event.setDescritor(descritor);
            event.setDirector(director);
            event.setSubmissions(new ArrayList<>());
            event.setSessions(new ArrayList<>());

            user.addEvent(event);
            AdminUser userHandler = KeeperInterface.createAdminUser();
            userHandler.setEvents(user.getEvents());

            stateController.setCurrentEvent(event);
            crudController.eventCRUD.createEvent(event);
            crudController.userCRUD.updateUser(user.getUuid(), userHandler);

            return true;
        }
        return false;
    }

    public void updateEventDescritor(String descritor){
        GreatEvent source = KeeperInterface.createGreatEvent();
        source.setDescritor(descritor);
        crudController.eventCRUD.updateEvent(stateController.getCurrentEvent().getUuid(), source);
    }
    public void updateEventDirector(String director){
        GreatEvent source = KeeperInterface.createGreatEvent();
        source.setDirector(director);
        crudController.eventCRUD.updateEvent(stateController.getCurrentEvent().getUuid(), source);
    }
    public void updateEventStartDate(Date startDate){
        GreatEvent source = KeeperInterface.createGreatEvent();
        source.setStartDate(startDate);
        crudController.eventCRUD.updateEvent(stateController.getCurrentEvent().getUuid(), source);
    }
    public void updateEventEndDate(Date endDate){
        GreatEvent source = KeeperInterface.createGreatEvent();
        source.setEndDate(endDate);
        crudController.eventCRUD.updateEvent(stateController.getCurrentEvent().getUuid(), source);
    }
    public void addEventSubmission(String descritor){
        Submission submission = KeeperInterface.createSubmission();
        submission.setUuid(UUID.randomUUID());
        submission.setUserUuid(stateController.getCurrentUser().getUuid());
        submission.setEventUuid(stateController.getCurrentEvent().getUuid());
        submission.setDescritor(descritor);
        submission.setDate(new Date());

        stateController.getCurrentEvent().addSubmission(submission);

        GreatEvent eventHandler = KeeperInterface.createGreatEvent();
        eventHandler.setSubmissions(stateController.getCurrentEvent().getSubmissions());

        crudController.submissionCRUD.createSubmission(submission);
        crudController.eventCRUD.updateEvent(stateController.getCurrentEvent().getUuid(), eventHandler);
    }
    public void changeCurrentEvent(UUID eventUuid){
        stateController.setCurrentEvent(crudController.eventCRUD.returnEvent(eventUuid));
    }
    public void closeCurrentEvent(){
        stateController.setCurrentEvent(null);
    }

    public Collection<GreatEvent> getAllEvents() {
        return crudController.eventCRUD.returnEvent();
    }

    public Collection<GreatEvent> getAllEventsByUser() {
        if(stateController.getCurrentUser() instanceof AdminUser user) return user.getEvents();
        return getAllEvents();
    }
}
