package br.upe.menus;

import br.upe.controllers.StateController;
import br.upe.controllers.SessionController;
import br.upe.pojos.Subscription;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.UUID;

public class SubscriptionMenu {

    private final SessionController sessionController;
    private final StateController stateController;
    private final Scanner scanner = new Scanner(System.in);

    public SubscriptionMenu(SessionController sessionController, StateController stateController) {
        this.sessionController = sessionController;
        this.stateController = stateController;
    }

    public void displayMenu() {
        boolean running = true;
        while (running) {
            System.out.println("Subscription Menu");
            System.out.println("1. Create New Subscription");
            System.out.println("2. Delete Subscription");
            System.out.println("3. Update Subscription");
            System.out.println("4. View Subscription");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    createNewSubscription();
                    break;
                case 2:
                    deleteSubscription();
                    break;
                case 3:
                    updateSubscription();
                    break;
                case 4:
                    viewSubscription();
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void createNewSubscription() {
        System.out.println("Enter session UUID (existing session):");
        UUID sessionUuid = UUID.fromString(scanner.nextLine());
        System.out.println("Enter user UUID (existing user):");
        UUID userUuid = UUID.fromString(scanner.nextLine());
        System.out.println("Enter subscription date (yyyy-MM-dd):");
        String dateStr = scanner.nextLine();
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
            Subscription subscription = new Subscription();
            subscription.setUuid(UUID.randomUUID());
            subscription.setSessionUuid(sessionUuid);
            subscription.setUserUuid(userUuid);
            subscription.setDate(date);
            sessionController.addSubscriptionToSession(subscription);
            System.out.println("Subscription created successfully.");
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
        }
    }

    private void deleteSubscription() {
        System.out.println("Enter subscription UUID to delete:");
        UUID subscriptionUuid = UUID.fromString(scanner.nextLine());
        sessionController.deleteSubscriptionFromSession(subscriptionUuid);
        System.out.println("Subscription deleted successfully.");
    }

    private void updateSubscription() {
        System.out.println("Enter subscription UUID to update:");
        UUID subscriptionUuid = UUID.fromString(scanner.nextLine());
        System.out.println("Enter new session UUID (if changing session):");
        UUID newSessionUuid = UUID.fromString(scanner.nextLine());
        System.out.println("Enter new user UUID (if changing user):");
        UUID newUserUuid = UUID.fromString(scanner.nextLine());
        System.out.println("Enter new date (yyyy-MM-dd):");
        String dateStr = scanner.nextLine();
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
            Subscription subscription = new Subscription();
            subscription.setUuid(subscriptionUuid);
            subscription.setSessionUuid(newSessionUuid);
            subscription.setUserUuid(newUserUuid);
            subscription.setDate(date);
            sessionController.updateSubscription(subscription);
            System.out.println("Subscription updated successfully.");
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
        }
    }

    private void viewSubscription() {
        System.out.println("Enter subscription UUID to view:");
        UUID subscriptionUuid = UUID.fromString(scanner.nextLine());
        Subscription subscription = sessionController.getSubscription(subscriptionUuid);
        if (subscription != null) {
            System.out.println("Subscription UUID: " + subscription.getUuid());
            System.out.println("Session UUID: " + subscription.getSessionUuid());
            System.out.println("User UUID: " + subscription.getUserUuid());
            System.out.println("Date: " + new SimpleDateFormat("yyyy-MM-dd").format(subscription.getDate()));
        } else {
            System.out.println("Subscription not found.");
        }
    }
}
