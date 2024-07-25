package br.upe.UserInterface;

import br.upe.controllers.AuthController;


import br.upe.controllers.CRUDController;
import br.upe.controllers.EventController;
import br.upe.controllers.SessionController;
import br.upe.controllers.StateController;
import br.upe.controllers.UserController;

public class App {
    public static void main(String[] args) {
        StateController stateController = new StateController();
        CRUDController crudController = new CRUDController();
        AuthController authController = new AuthController(stateController, crudController);
        UserController userController = new UserController(stateController, crudController);
        EventController eventController = new EventController(stateController, crudController);
        SessionController sessionController = new SessionController(stateController, crudController);

        LoginMenu loginMenu = new LoginMenu(authController);
        MainMenu mainMenu = new MainMenu(userController, eventController, sessionController, loginMenu);

        mainMenu.displayMainMenu();

    }
}