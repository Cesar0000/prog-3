package br.upe.controllers;

import br.upe.pojos.GreatEvent;
import br.upe.pojos.User;

public class StateController {
    private User currentUser;
    private GreatEvent currentEvent;

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
    public User getCurrentUser() {
        return this.currentUser;
    }
    public GreatEvent getCurrentEvent() {
        return currentEvent;
    }
    public void setCurrentEvent(GreatEvent currentEvent) {
        this.currentEvent = currentEvent;
    }



}
