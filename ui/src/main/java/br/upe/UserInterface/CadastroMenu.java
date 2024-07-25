package br.upe.UserInterface;

import br.upe.controllers.EventController;
import br.upe.controllers.SessionController;
import br.upe.controllers.StateController;

import java.util.Date;
import java.util.Scanner;

public class CadastroMenu {
    private final EventController eventController;
    private final SessionController sessionController;
    private final Scanner scanner;
    private final StateController stateController;

    public CadastroMenu(EventController eventController, SessionController sessionController, StateController stateController) {
        this.eventController = eventController;
        this.sessionController = sessionController;
        this.scanner = new Scanner(System.in);
        this.stateController = stateController;
    }

    public void displayCadastroMenu() {
        boolean running = true;
        while (running) {
            System.out.println("Menu de Cadastro:");
            System.out.println("1. Cadastrar evento");
            System.out.println("2. Cadastrar sessão");
            System.out.println("3. Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    registerEvent();
                    break;
                case 2:
                    registerSession();
                    break;
                case 3:
                    running = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void registerEvent() {
        System.out.print("Digite o descritor do evento: ");
        String descritor = scanner.nextLine();
        System.out.print("Digite o diretor do evento: ");
        String director = scanner.nextLine();
        if (eventController.createNewEvent(descritor, director)) {
            System.out.println("Evento cadastrado com sucesso.");
        } else {
            System.out.println("Falha ao cadastrar evento.");
        }
    }

    private void registerSession() {
        System.out.print("Digite o descritor da sessão: ");
        String descritor = scanner.nextLine();
        System.out.print("Digite a data de início (YYYY-MM-DD): ");
        String startDateString = scanner.nextLine();
        System.out.print("Digite a data de término (YYYY-MM-DD): ");
        String endDateString = scanner.nextLine();

        try {
            Date startDate = java.sql.Date.valueOf(startDateString);
            Date endDate = java.sql.Date.valueOf(endDateString);
            if (sessionController.createNewSession(descritor, startDate, endDate)) {
                System.out.println("Sessão cadastrada com sucesso.");
            } else {
                System.out.println("Falha ao cadastrar sessão.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Datas fornecidas são inválidas.");
        }
    }
}
