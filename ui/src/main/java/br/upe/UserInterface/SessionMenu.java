package br.upe.UserInterface;

import br.upe.controllers.SessionController;
import br.upe.controllers.StateController;

import java.util.Date;
import java.util.Scanner;

public class SessionMenu {

    private final SessionController sessionController;
    private StateController stateController = null;
    private final Scanner scanner = new Scanner(System.in);

    public SessionMenu(SessionController sessionController) {
        this.sessionController = sessionController;
        this.stateController = stateController;
    }

    public void displaySessionMenu() {
        boolean running = true;
        while (running) {
            System.out.println("Session Menu");
            System.out.println("1. Create New Session");
            System.out.println("2. Update Session Descritor");
            System.out.println("3. Update Session Start Date");
            System.out.println("4. Update Session End Date");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

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
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void createNewSession() {
        System.out.println("Enter session descritor:");
        String descritor = scanner.nextLine();
        if (sessionController.createNewSession(descritor)) {
            System.out.println("Session created successfully.");
        } else {
            System.out.println("Failed to create session. Ensure you are logged in as an admin.");
        }
    }

    private void updateSessionDescritor() {
        if (stateController.getCurrentSession() == null) {
            System.out.println("No session selected.");
            return;
        }
        System.out.println("Enter new session descritor:");
        String descritor = scanner.nextLine();
        sessionController.updateSessionDescritor(descritor);
        System.out.println("Session descritor updated.");
    }

    private void updateSessionStartDate() {
        if (stateController.getCurrentSession() == null) {
            System.out.println("No session selected.");
            return;
        }
        System.out.println("Enter new start date (yyyy-MM-dd):");
        String startDateStr = scanner.nextLine();
        try {
            Date startDate = new Date(startDateStr);  // Simplified date parsing
            if (sessionController.updateSessionStartDate(startDate)) {
                System.out.println("Session start date updated.");
            } else {
                System.out.println("Invalid start date. It must be after the event start date.");
            }
        } catch (Exception e) {
            System.out.println("Invalid date format.");
        }
    }

    private void updateSessionEndDate() {
        if (stateController.getCurrentSession() == null) {
            System.out.println("No session selected.");
            return;
        }
        System.out.println("Enter new end date (yyyy-MM-dd):");
        String endDateStr = scanner.nextLine();
        try {
            Date endDate = new Date(endDateStr);  // Simplified date parsing
            if (sessionController.updateSessionEndDate(endDate)) {
                System.out.println("Session end date updated.");
            } else {
                System.out.println("Invalid end date. It must be before the event end date.");
            }
        } catch (Exception e) {
            System.out.println("Invalid date format.");
        }
    }
}
