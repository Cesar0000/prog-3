package br.upe.controllers;

public interface ControllersInterface {
    static AuthController newAuthController(StateController stateController, CRUDController crudController) {
        return new AuthController(stateController, crudController);
    }
    static SessionController newSessionController(StateController stateController, CRUDController crudController) {
        return new SessionController(stateController, crudController);
    }
    static StateController newStateController() {
        return new StateController();
    }
    static UserController newUserController(StateController stateController, CRUDController crudController) {
        return new UserController(stateController, crudController);
    }
    static CRUDController newCRUDController() {
        return new CRUDController();
    }
    static EventController newEventController(StateController stateController, CRUDController crudController) {
        return new EventController(stateController, crudController);
    }
    static SubmissionController newSubmissionController(StateController stateController, CRUDController crudController) {
        return new SubmissionController(stateController, crudController);
    }
    static SubscriptionController newSubscriptionController(StateController stateController, CRUDController crudController) {
        return new SubscriptionController(stateController, crudController);
    }
}
