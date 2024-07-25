package br.upe.UserInterface;

import br.upe.controllers.EventController;
import br.upe.controllers.SessionController;
import br.upe.controllers.StateController;
import br.upe.pojos.AdminUser;
import br.upe.pojos.User;

import java.util.Scanner;

public class AppMenu {
    private final StateController stateController;
    private final EventController eventController;
    private final SessionController sessionController;
    private final Scanner scanner;

    public AppMenu(StateController stateController, EventController eventController, SessionController sessionController) {
        this.stateController = stateController;
        this.eventController = eventController;
        this.sessionController = sessionController;
        this.scanner = new Scanner(System.in);
    }

    public void displayAppMenu() {
        boolean running = true;
        while (running) {
            User currentUser = stateController.getCurrentUser();
            System.out.println("Menu Principal:");
            if (currentUser != null) {
                System.out.println("Bem-vindo, " + currentUser.getName());
                if (currentUser instanceof AdminUser) {
                    System.out.println("1. Menu de Eventos");
                    System.out.println("2. Menu de Sessões");
                }
                System.out.println("3. Menu de Cadastro");
                System.out.println("4. Sair");
                System.out.print("Escolha uma opção: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        if (currentUser instanceof AdminUser) {
                            EventMenu eventMenu = new EventMenu(eventController, stateController);
                            eventMenu.displayEventMenu();
                        } else {
                            System.out.println("Acesso negado. Somente administradores podem acessar este menu.");
                        }
                        break;
                    case 2:
                        if (currentUser instanceof AdminUser) {
                            SessionMenu sessionMenu = new SessionMenu(sessionController, stateController);
                            sessionMenu.displaySessionMenu();
                        } else {
                            System.out.println("Acesso negado. Somente administradores podem acessar este menu.");
                        }
                        break;
                    case 3:
                        CadastroMenu cadastroMenu = new CadastroMenu(eventController, sessionController, stateController);
                        cadastroMenu.displayCadastroMenu();
                        break;
                    case 4:
                        running = false;
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } else {
                System.out.println("Usuário não autenticado.");
                running = false;
            }
        }
    }
}
