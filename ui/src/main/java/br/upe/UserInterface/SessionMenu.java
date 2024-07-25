package br.upe.UserInterface;

import br.upe.controllers.SessionController;
import br.upe.controllers.StateController;
import br.upe.pojos.AdminUser;

import java.util.Date;
import java.util.Scanner;

public class SessionMenu {
    private final SessionController sessionController;
    private final Scanner scanner;
    private final StateController stateController;

    public SessionMenu(SessionController sessionController, StateController stateController) {
        this.sessionController = sessionController;
        this.scanner = new Scanner(System.in);
        this.stateController = stateController;
    }

    public void displaySessionMenu() {
        if (!(stateController.getCurrentUser() instanceof AdminUser)) {
            System.out.println("Acesso negado. Somente administradores podem acessar este menu.");
            return;
        }

        boolean running = true;
        while (running) {
            System.out.println("Menu de Sessão:");
            System.out.println("1. Criar nova sessão");
            System.out.println("2. Atualizar descritor da sessão");
            System.out.println("3. Atualizar data de início da sessão");
            System.out.println("4. Atualizar data de término da sessão");
            System.out.println("5. Adicionar inscrição à sessão");
            System.out.println("6. Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createNewSession();
                    break;
                case 2:
                    updateSessionDescritor();
                    break;
                case 3:
                    updateSessionStartDate();
                    break;
                case 4:
                    updateSessionEndDate();
                    break;
                case 5:
                    addSessionSubscription();
                    break;
                case 6:
                    running = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void createNewSession() {
        System.out.print("Digite o descritor da nova sessão: ");
        String descritor = scanner.nextLine();
        if (sessionController.createNewSession(descritor)) {
            System.out.println("Sessão criada com sucesso.");
        } else {
            System.out.println("Falha ao criar sessão.");
        }
    }

    private void updateSessionDescritor() {
        System.out.print("Digite o novo descritor da sessão: ");
        String descritor = scanner.nextLine();
        sessionController.updateSessionDescritor(descritor);
        System.out.println("Descritor da sessão atualizado com sucesso.");
    }

    private void updateSessionStartDate() {
        System.out.print("Digite a nova data de início (YYYY-MM-DD): ");
        String dateString = scanner.nextLine();
        Date startDate = java.sql.Date.valueOf(dateString);
        if (sessionController.updateSessionStartDate(startDate)) {
            System.out.println("Data de início da sessão atualizada com sucesso.");
        } else {
            System.out.println("Data de início inválida.");
        }
    }

    private void updateSessionEndDate() {
        System.out.print("Digite a nova data de término (YYYY-MM-DD): ");
        String dateString = scanner.nextLine();
        Date endDate = java.sql.Date.valueOf(dateString);
        if (sessionController.updateSessionEndDate(endDate)) {
            System.out.println("Data de término da sessão atualizada com sucesso.");
        } else {
            System.out.println("Data de término inválida.");
        }
    }

    private void addSessionSubscription() {
        sessionController.addSessionsSubscription();
        System.out.println("Inscrição adicionada à sessão.");
    }
}
