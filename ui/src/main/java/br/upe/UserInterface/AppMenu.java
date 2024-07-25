package br.upe.UserInterface;

import br.upe.controllers.AuthController;
import br.upe.controllers.EventController;
import br.upe.controllers.SessionController;
import br.upe.controllers.UserController;

import java.util.Scanner;

public class AppMenu {
    private final LoginMenu loginMenu;
    private final MainMenu mainMenu;
    private final SessionMenu sessionMenu;
    private final EventMenu eventMenu;
    private final Scanner scanner = new Scanner(System.in);

    public AppMenu(AuthController authController, UserController userController, EventController eventController, SessionController sessionController) {
        this.loginMenu = new LoginMenu(authController);
        this.mainMenu = new MainMenu(userController, eventController, sessionController, loginMenu);
        this.sessionMenu = new SessionMenu(sessionController);
        this.eventMenu = new EventMenu(eventController);
    }

    public void displayAppMenu() {
        boolean running = true;

        while (running) {
            System.out.println("App Menu:");
            System.out.println("1. Menu de Login");
            System.out.println("2. Menu Principal");
            System.out.println("3. Menu de Sessões");
            System.out.println("4. Menu de Eventos");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    loginMenu.displayLoginMenu();
                    break;
                case 2:
                    mainMenu.displayMainMenu();
                    break;
                case 3:
                    sessionMenu.displaySessionMenu();
                    break;
                case 4:
                    eventMenu.displayEventMenu();
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }

        System.out.println("Aplicação encerrada.");
    }
}
