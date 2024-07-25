package br.upe.UserInterface;

import br.upe.controllers.EventController;
import br.upe.controllers.StateController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class EventMenu {

    private final EventController eventController;
    private StateController stateController = null;
    private final Scanner scanner = new Scanner(System.in);

    public EventMenu(EventController eventController) {
        this.eventController = eventController;
        this.stateController = stateController;
    }

    public void displayEventMenu() {
        boolean running = true;
        while (running) {
            System.out.println("Event Menu");
            System.out.println("1. Create New Event");
            System.out.println("2. Update Event Descritor");
            System.out.println("3. Update Event Director");
            System.out.println("4. Update Event Start Date");
            System.out.println("5. Update Event End Date");
            System.out.println("6. Add Submission to Event");
            System.out.println("7. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

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
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void createNewEvent() {
        System.out.println("Enter event descritor:");
        String descritor = scanner.nextLine();
        System.out.println("Enter event director:");
        String director = scanner.nextLine();
        if (eventController.createNewEvent(descritor, director)) {
            System.out.println("Event created successfully.");
        } else {
            System.out.println("Failed to create event. Ensure you are logged in as an admin.");
        }
    }

    private void updateEventDescritor() {
        if (stateController.getCurrentEvent() == null) {
            System.out.println("No event selected.");
            return;
        }
        System.out.println("Enter new event descritor:");
        String descritor = scanner.nextLine();
        eventController.updateEventDescritor(descritor);
        System.out.println("Event descritor updated.");
    }

    private void updateEventDirector() {
        if (stateController.getCurrentEvent() == null) {
            System.out.println("No event selected.");
            return;
        }
        System.out.println("Enter new event director:");
        String director = scanner.nextLine();
        eventController.updateEventDirector(director);
        System.out.println("Event director updated.");
    }

    private void updateEventStartDate() {
        if (stateController.getCurrentEvent() == null) {
            System.out.println("No event selected.");
            return;
        }
        System.out.println("Enter new start date (yyyy-MM-dd):");
        String startDateStr = scanner.nextLine();
        try {
            Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateStr);
            eventController.updateEventStartDate(startDate);
            System.out.println("Event start date updated.");
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
        }
    }

    private void updateEventEndDate() {
        if (stateController.getCurrentEvent() == null) {
            System.out.println("No event selected.");
            return;
        }
        System.out.println("Enter new end date (yyyy-MM-dd):");
        String endDateStr = scanner.nextLine();
        try {
            Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDateStr);
            eventController.updateEventEndDate(endDate);
            System.out.println("Event end date updated.");
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
        }
    }

    private void addEventSubmission() {
        if (stateController.getCurrentEvent() == null) {
            System.out.println("No event selected.");
            return;
        }
        System.out.println("Enter submission descritor:");
        String descritor = scanner.nextLine();
        eventController.addEventSubmission(descritor);
        System.out.println("Submission added to event.");
    }
}
