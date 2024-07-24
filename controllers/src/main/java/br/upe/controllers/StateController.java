package br.upe.controllers;

import br.upe.pojos.*;

public class StateController {
    private User currentUser;
    private GreatEvent currentEvent;
    private Session  currentSession;
    private Submission currentSubmission;
    private Subscription currentSubscription;

    public Session getCurrentSession() {
        return currentSession;
    }
    public void setCurrentSession(Session currentSession) {
        this.currentSession = currentSession;
    }
    public Submission getCurrentSubmission() {
        return currentSubmission;
    }
    public void setCurrentSubmission(Submission currentSubmission) {
        this.currentSubmission = currentSubmission;
    }
    public Subscription getCurrentSubscription() {
        return currentSubscription;
    }
    public void setCurrentSubscription(Subscription currentSubscription) {
        this.currentSubscription = currentSubscription;
    }
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
