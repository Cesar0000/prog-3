package br.upe.UserInterface;

import br.upe.controllers.EventController;
import br.upe.controllers.StateController;
import br.upe.pojos.AdminUser;

import java.util.Date;
import java.util.Scanner;

public class EventMenu {
    private final EventController eventController;
    private final Scanner scanner;
    private final StateController stateController;

    public EventMenu(EventController eventController, StateController stateController) {
        this.eventController = eventController;
        this.scanner = new Scanner(System.in);
        this.stateController = stateController;
    }

    public void displayEventMenu() {
        if (!(stateController.getCurrentUser() instanceof AdminUser)) {
            System.out.println("Acesso negado. Somente administradores podem acessar este menu.");
            return;
        }

        boolean running = true;
        while (running) {
            System.out.println("Menu de Evento:");
            System.out.println("1. Criar novo evento");
            System.out.println("2. Atualizar descritor do evento");
            System.out.println("3. Atualizar diretor do evento");
            System.out.println("4. Atualizar data de início do evento");
            System.out.println("5. Atualizar data de término do evento");
            System.out.println("6. Adicionar submissão ao evento");
            System.out.println("7. Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createNewEvent();
                    break;
                case 2:
                    updateEventDescritor();
                    break;
                case 3:
                    updateEventDirector();
                    break;
                case 4:
                    updateEventStartDate();
                    break;
                case 5:
                    updateEventEndDate();
                    break;
                case 6:
                    addEventSubmission();
                    break;
                case 7:
                    running = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void createNewEvent() {
        System.out.print("Digite o descritor do novo evento: ");
        String descritor = scanner.nextLine();
        System.out.print("Digite o diretor do novo evento: ");
        String director = scanner.nextLine();
        if (eventController.createNewEvent(descritor, director)) {
            System.out.println("Evento criado com sucesso.");
        } else {
            System.out.println("Falha ao criar evento.");
        }
    }

    private void updateEventDescritor() {
        System.out.print("Digite o novo descritor do evento: ");
        String descritor = scanner.nextLine();
        eventController.updateEventDescritor(descritor);
        System.out.println("Descritor do evento atualizado com sucesso.");
    }

    private void updateEventDirector() {
        System.out.print("Digite o novo diretor do evento: ");
        String director = scanner.nextLine();
        eventController.updateEventDirector(director);
        System.out.println("Diretor do evento atualizado com sucesso.");
    }

    private void updateEventStartDate() {
        System.out.print("Digite a nova data de início (YYYY-MM-DD): ");
        String dateString = scanner.nextLine();
        Date startDate = java.sql.Date.valueOf(dateString);
        eventController.updateEventStartDate(startDate);
        System.out.println("Data de início do evento atualizada com sucesso.");
    }

    private void updateEventEndDate() {
        System.out.print("Digite a nova data de término (YYYY-MM-DD): ");
        String dateString = scanner.nextLine();
        Date endDate = java.sql.Date.valueOf(dateString);
        eventController.updateEventEndDate(endDate);
        System.out.println("Data de término do evento atualizada com sucesso.");
    }

    private void addEventSubmission() {
        System.out.print("Digite o descritor da submissão: ");
        String descritor = scanner.nextLine();
        eventController.addEventSubmission(descritor);
        System.out.println("Submissão adicionada ao evento.");
    }
}
