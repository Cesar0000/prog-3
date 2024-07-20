package br.upe.controllers;

import br.upe.pojos.User;

public class StateController {
    private User currentUser;

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
    public User getCurrentUser() {
        return this.currentUser;
    }



}
