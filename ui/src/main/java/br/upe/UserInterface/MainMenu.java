package br.upe.UserInterface;

import br.upe.controllers.*;
import br.upe.pojos.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.UUID;

public class MainMenu {
    private final StateController stateController = ControllersInterface.newStateController();
    private final CRUDController crudController = ControllersInterface.newCRUDController();
    private final AuthController authController = ControllersInterface.newAuthController(stateController, crudController);
    private final UserController userController = ControllersInterface.newUserController(stateController, crudController);
    private final EventController eventController = ControllersInterface.newEventController(stateController, crudController);
    private final SessionController sessionController = ControllersInterface.newSessionController(stateController, crudController);
    private final SubscriptionController subscriptionController = ControllersInterface.newSubscriptionController(stateController, crudController);
    private final SubmissionController submissionController = ControllersInterface.newSubmissionController(stateController, crudController);

    public void displayStartMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("App Menu:");
            System.out.println("1. Menu de Login");
            System.out.println("2. Sair");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    displayAuthMenu();
                    break;
                case 2:
                    running = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

        }

        System.out.println("Aplicação encerrada.");
    }

    public void displayAuthMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("Event Menu");
            System.out.println("1. Login");
            System.out.println("2. Create new User");
            System.out.println("3. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    displayLoginMenu();
                    displayHomeMenu();

                    break;
                case 2:
                    do {
                        choice = 4;
                        System.out.println("Create User Menu");
                        System.out.println("1. Create new Admin");
                        System.out.println("2. Create new User");
                        System.out.println("3. Exit");
                        choice = scanner.nextInt();
                        switch (choice) {
                            case 1:
                                createNewAdmin();
                                break;
                            case 2:
                                createNewUser();
                                break;
                            case 3:
                                running = false;
                                break;
                            default:
                                System.out.println("Invalid choice. Please try again.");
                        }
                    } while (choice >= 4);
                        break;
                        case 3:
                            running = false;
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void createNewUser(){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your e-mail: ");
        String email = scanner.nextLine().trim();
        System.out.println();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine().trim();
        System.out.println();

        if(authController.createNewUser(email, password)){
            authController.login(email, password);

            System.out.print("Enter your full name without abbreviations: ");
            String name = scanner.nextLine().trim();
            System.out.println();
            userController.updateUserName(name);
            authController.logout();
            System.out.println("User created!");
        }

    }
    public void createNewAdmin(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your e-mail: ");
        String email = scanner.nextLine().trim();
        System.out.println();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine().trim();
        System.out.println();

        if(authController.createNewAdmin(email, password)){
            authController.login(email, password);

            System.out.print("Enter your full name without abbreviations: ");
            String name = scanner.nextLine().trim();
            System.out.println();
            userController.updateUserName(name);
            authController.logout();
            System.out.println("User created!");
        }

    }

    public void displayLoginMenu(){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your e-mail: ");
        String email = scanner.nextLine().trim();
        scanner.nextLine();
        System.out.println();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine().trim();
        scanner.nextLine();
        System.out.println();

        if(authController.login(email, password)) {
            System.out.println("Logged successfully!");
            return;
        } else {
            displayLoginMenu();
        }
    }

    public void displayHomeMenu(){
        if(stateController.getCurrentUser() instanceof AdminUser){
            displayAdminMenu();
        } else {
            displayUserMenu();
        }
        authController.logout();
    }

    public void displayUserMenu(){
        boolean running = true;
        Scanner scanner = new Scanner(System.in);
        while (running){
            System.out.println("User Menu");
            System.out.println("1. List Events");
            System.out.println("2. List Subscriptions");
            System.out.println("3. List Submissions");
            System.out.println("4. Update Password");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewAllEvents();
                    break;
                case 2:
                    viewSubscriptions();
                    break;
                case 3:
                   // viewSubmissions();
                    break;
                case 4:
                    System.out.print("Enter your current password: ");
                    String password = scanner.nextLine().trim();
                    System.out.println();
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }


    }
    public void displayAdminMenu(){
        boolean running = true;
        while(running){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Admin Menu");
            System.out.println("1. Create Event");
            System.out.println("2. List Events");
            System.out.println("3. Update Password");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createNewEvent();
                    break;
                case 2:
                    viewEventsByUser();
                    break;
                case 3:
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }

    }

    public void displayEventMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("Event Menu: " + stateController.getCurrentEvent().getDescritor());
            System.out.println("1. Manage Submissions");
            System.out.println("2. Update Event Descritor");
            System.out.println("3. Update Event Director");
            System.out.println("4. Update Event Start Date");
            System.out.println("5. Update Event End Date");
            System.out.println("6. Manage Session");

            System.out.println("7. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    manageSubmissions();
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
                    manageSession();
                    break;
                case 7:
                   return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void manageSession(){
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("Menu Session from: " + stateController.getCurrentEvent().getDescritor());
            System.out.println("1. Create Session");
            System.out.println("2. List Sessions");
            System.out.println("3. Exit");


            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createNewSession();
                    displaySessionMenu();
                    break;
                case 2:
                    viewAllSessions();
                    displaySessionMenu();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

    }

    public void viewAllSessions(){
        Scanner scanner = new Scanner(System.in);
        int counter = 1;
        for(Session session : stateController.getCurrentEvent().getSessions()){
            if (session != null) {
                System.out.println("index [ " + counter + " ]");
                System.out.println("Session: " + session.getDescritor());
                if(session.getStartDate() != null) System.out.println("Start Date: " + new SimpleDateFormat("yyyy-MM-dd").format(session.getStartDate()));
                if(session.getEndDate() != null)System.out.println("End Date: " + new SimpleDateFormat("yyyy-MM-dd").format(session.getEndDate()));
                System.out.println();
                counter++;
            }
        }

        System.out.println("Select session index to manage or type 0 to exit");

        int choice = scanner.nextInt();
        scanner.nextLine();

        if(choice != 0 && choice <= counter){
            int innerCount = 1;
            System.out.println("outfor");
            for(Session session : stateController.getCurrentEvent().getSessions()){
                if(innerCount == choice){
                    System.out.println("ifinner");
                    sessionController.closeCurrentSession();
                    sessionController.changeCurrentSession(session.getUuid());
                    return;
                } else{
                    innerCount++;
                }
            }
        }

    }

    private void manageSubmissions(){
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("Submissions Menu: ");
            System.out.println("1. List Submissions");
            System.out.println("2. Remove Submission");
            System.out.println("3. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    //listSubmissions();
                    break;
                case 2:
                    //removeSubmission();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

    }

    private void createNewEvent() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter event descritor:");
        String descritor = scanner.nextLine();
        scanner.nextLine();
        System.out.println("Enter event director:");
        String director = scanner.nextLine();
        scanner.nextLine();
        if (eventController.createNewEvent(descritor, director)) {
            System.out.println("Event created successfully.");
            displayEventMenu();
        } else {
            System.out.println("Failed to create event. Ensure you are logged in as an admin.");
        }

    }

    private void updateEventDescritor() {
        Scanner scanner = new Scanner(System.in);
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
        Scanner scanner = new Scanner(System.in);
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
        Scanner scanner = new Scanner(System.in);
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
        Scanner scanner = new Scanner(System.in);
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
        Scanner scanner = new Scanner(System.in);
        if (stateController.getCurrentEvent() == null) {
            System.out.println("No event selected.");
            return;
        }
        System.out.println("Enter submission descritor:");
        String descritor = scanner.nextLine();
        eventController.addEventSubmission(descritor);
        System.out.println("Submission added to event.");
    }

    public void displaySessionMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("Session Menu: " + stateController.getCurrentSession().getDescritor());
            System.out.println("1. List Subscriptions");
            System.out.println("2. Update Session Descritor");
            System.out.println("3. Update Session Start Date");
            System.out.println("4. Update Session End Date");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    //listSessionSubscription();
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
        Scanner scanner = new Scanner(System.in);
        System.out.println("Título da sessão:");
        String descritor = scanner.nextLine();
        if (sessionController.createNewSession(descritor)) {
            System.out.println("Session created successfully.");
        } else {
            System.out.println("Failed to create session. Ensure you are logged in as an admin.");
        }
    }

    private void updateSessionDescritor() {
        Scanner scanner = new Scanner(System.in);
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
        Scanner scanner = new Scanner(System.in);
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
        Scanner scanner = new Scanner(System.in);
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

    public void displaySubscriptionMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("Subscription Menu");
            System.out.println("1. Create New Subscription");
            System.out.println("2. Delete Subscription");
            System.out.println("3. View Subscriptions");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createNewSubscription();
                    break;
                case 2:
                    deleteSubscription();
                    break;
                case 3:
                    viewSubscriptions();
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void createNewSubscription() {
        Scanner scanner = new Scanner(System.in);
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
            crudController.subscriptionCRUD.createSubscription(subscription);
            System.out.println("Subscription created successfully.");
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
        }
    }

    private void deleteSubscription() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter subscription UUID to delete:");
        UUID subscriptionUuid = UUID.fromString(scanner.nextLine());
        subscriptionController.removeSubscription(subscriptionUuid);
        System.out.println("Subscription deleted successfully.");
    }

    private void viewSubscriptions() {
        for(Subscription subscription : stateController.getCurrentUser().getSubscriptions()){
            if (subscription != null) {
                System.out.println("Subscription UUID: " + subscription.getUuid());
                System.out.println("Session UUID: " + subscription.getSessionUuid());
                System.out.println("User UUID: " + subscription.getUserUuid());
                System.out.println("Date: " + new SimpleDateFormat("yyyy-MM-dd").format(subscription.getDate()));
                System.out.println();
            }
        }
    }

    private void viewAllEvents() {
        for(GreatEvent event : eventController.getAllEvents()){
            if (event != null) {
                System.out.println("Event: " + event.getDescritor());
                System.out.println("Director: " + event.getDirector());
                System.out.println("Start Date: " + new SimpleDateFormat("yyyy-MM-dd").format(event.getStartDate()));
                System.out.println("End Date: " + new SimpleDateFormat("yyyy-MM-dd").format(event.getEndDate()));
                System.out.println();
            }
        }
    }

    private void viewEventsByUser() {
        Scanner scanner = new Scanner(System.in);
        int counter = 1;
        for(GreatEvent event : eventController.getAllEventsByUser()){
            if (event != null) {
                System.out.println("index [ " + counter + " ]");
                System.out.println("Event: " + event.getDescritor());
                System.out.println("Director: " + event.getDirector());
                if(event.getStartDate() != null) System.out.println("Start Date: " + new SimpleDateFormat("yyyy-MM-dd").format(event.getStartDate()));
                if(event.getEndDate() != null)System.out.println("End Date: " + new SimpleDateFormat("yyyy-MM-dd").format(event.getEndDate()));
                System.out.println();
                counter++;
            }
        }

        System.out.println("Select event index to manage or type 0 to exit");

        int choice = scanner.nextInt();
        scanner.nextLine();

        if(choice != 0 && choice <= counter){
            int innerCount = 1;
            for(GreatEvent event : eventController.getAllEventsByUser()){
                if(innerCount == choice){
                    eventController.closeCurrentEvent();
                    eventController.changeCurrentEvent(event.getUuid());
                    displayEventMenu();
                    return;
                } else{
                    innerCount++;
                }
            }
        }
    }
}