package br.upe.UserInterface;

import br.upe.controllers.*;
import br.upe.operations.HasherInterface;
import br.upe.pojos.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainMenu {
    private final StateController stateController = ControllersInterface.newStateController();
    private final CRUDController crudController = ControllersInterface.newCRUDController();
    private final AuthController authController = ControllersInterface.newAuthController(stateController, crudController);
    private final UserController userController = ControllersInterface.newUserController(stateController, crudController);
    private final EventController eventController = ControllersInterface.newEventController(stateController, crudController);
    private final SessionController sessionController = ControllersInterface.newSessionController(stateController, crudController);
    private final SubscriptionController subscriptionController = ControllersInterface.newSubscriptionController(stateController, crudController);
    private final SubmissionController submissionController = ControllersInterface.newSubmissionController(stateController, crudController);

    private final Scanner scanner = new Scanner(System.in);

    public void displayStartMenu() {
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
        boolean running = true;
        while (running) {
            System.out.println("Auth Menu");
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
                    createNewUserMenu();
                    break;
                case 3:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void createNewUserMenu() {
        boolean running = true;
        do {
            System.out.println("Create User Menu");
            System.out.println("1. Create new Admin");
            System.out.println("2. Create new User");
            System.out.println("3. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();
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
        } while (running);
    }

    public void createNewUser() {
        System.out.print("Enter your e-mail: ");
        String email = scanner.nextLine().trim();
        System.out.println();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine().trim();
        System.out.println();

        if (authController.createNewUser(email, password)) {
            authController.login(email, password);

            System.out.print("Enter your full name without abbreviations: ");
            String name = scanner.nextLine().trim();
            System.out.println();
            userController.updateUserName(name);
            authController.logout();
            System.out.println("User created!");
        }
    }

    public void createNewAdmin() {
        System.out.print("Enter your e-mail: ");
        String email = scanner.nextLine().trim();
        System.out.println();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine().trim();
        System.out.println();

        if (authController.createNewAdmin(email, password)) {
            authController.login(email, password);

            System.out.print("Enter your full name without abbreviations: ");
            String name = scanner.nextLine().trim();
            System.out.println();
            userController.updateUserName(name);
            authController.logout();
            System.out.println("User created!");
        }
    }

    public void displayLoginMenu() {
        System.out.print("Enter your e-mail: ");
        String email = scanner.nextLine().trim();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine().trim();

        System.out.println();

        if (authController.login(email, password)) {
            System.out.println("Logged in successfully!");
        } else {
            System.out.println("Invalid email or password. Please try again (1) or type (0) to go back.");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 0:
                    return;
                case 1:
                    displayLoginMenu();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void displayHomeMenu() {
        if (stateController.getCurrentUser() instanceof AdminUser) {
            displayAdminMenu();
        } else {
            displayUserMenu();
        }
        authController.logout();
    }

    public void displayUserMenu() {
        boolean running = true;
        while (running) {
            System.out.println("User Menu");
            System.out.println("1. Subscribe to Events");
            System.out.println("2. List Subscriptions");
            System.out.println("3. List Submissions");
            System.out.println("4. Update Password");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createSubscription();
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
                    if(HasherInterface.hash(password).equals(stateController.getCurrentUser().getPassword())){
                        String newPassword = scanner.nextLine().trim();
                        userController.updateUserPassword(newPassword);
                        scanner.nextLine();
                        System.out.println("Senha alterada com sucesso!.");
                    } else {
                        System.out.println("Senha atual incorreta.");
                    }
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }


    public void createSubscription() {
        // Verificar se o usuário está logado
        if (stateController.getCurrentUser() == null) {
            System.out.println("Por favor, faça login primeiro.");
            return;
        }

        System.out.println("Lista de Eventos Disponíveis:");
        viewAllEvents();
        // Listar todos os eventos disponíveis para inscrição
        Collection<GreatEvent> allEvents = eventController.getAllEvents();
        GreatEvent selectedEvent = eventController.getAllEvents().stream().toList().get(getValidIndexFromUser(allEvents.size()));
        stateController.setCurrentEvent(selectedEvent);

        // Criar a inscrição
        UUID userUuid = stateController.getCurrentUser().getUuid();
        UUID eventUuid = stateController.getCurrentEvent().getUuid();
        Date subscriptionDate = new Date();

        Subscription subscription = KeeperInterface.createSubscription();
        subscription.setUuid(userUuid);
        subscription.setUuid(eventUuid);
        subscription.setDate(subscriptionDate);

        // Tentar criar a inscrição
        if (crudController.subscriptionCRUD.createSubscription(subscription)) {
            System.out.println("Inscrição realizada com sucesso!");
        } else {
            System.out.println("Falha ao realizar a inscrição. Tente novamente.");
        }
    }

    private int getValidIndexFromUser(int size) {
        Scanner scanner = new Scanner(System.in);
        int index = -1;

        while (index < 0 || index > size) {
            System.out.print("Selecione o índice do evento para se inscrever ou 0 para sair: ");
            index = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha

            if (index == 0) {
                return -1; // Sair se o usuário digitar 0
            }

            if (index < 0 || index > size) {
                System.out.println("Índice inválido. Tente novamente.");
            }
        }

        return index - 1; // Ajuste para índice baseado em 0
    }


    public void displayAdminMenu() {
        boolean running = true;
        while (running) {
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
                    System.out.print("Enter your current password: ");
                    String password = scanner.nextLine().trim();
                    System.out.println();
                    if(HasherInterface.hash(password).equals(stateController.getCurrentUser().getPassword())){
                        String newPassword = scanner.nextLine().trim();
                        userController.updateUserPassword(newPassword);
                        System.out.println("Senha alterada com sucesso!.");
                    } else {
                        System.out.println("Senha atual incorreta.");
                    }
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
        boolean running = true;

        while (running) {
            System.out.println("Event Menu: " + stateController.getCurrentEvent().getDescritor());
            System.out.println("1. Manage Submissions");
            System.out.println("2. Update Event Descriptor");
            System.out.println("3. Update Event Director");
            System.out.println("4. Update Event Start Date");
            System.out.println("5. Update Event End Date");
            System.out.println("6. Manage Session");
            System.out.println("7. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline left-over

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
                    running = false;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void displayEventMenuForCommomUser(){
        boolean running = true;

        while (running) {
            System.out.println("Event Menu: " + stateController.getCurrentEvent().getDescritor());
            System.out.println("1. List Sessions");
            System.out.println("2. Create Submission");
            System.out.println("3. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline left-over

            switch (choice) {
                case 1:
                    viewAllSessions();
                    break;
                case 2:
                    manageSubmissions();
                    break;
                case 3:
                    running = false;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

    }
    private void manageSession() {
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
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void viewAllSessions() {
        int counter = 1;
        for (Session session : stateController.getCurrentEvent().getSessions()) {
            if (session != null) {
                System.out.println("index [ " + counter + " ]");
                System.out.println("Session: " + session.getDescritor());
                if (session.getStartDate() != null)
                    System.out.println("Start date: " + session.getStartDate().toString());
                if (session.getEndDate() != null)
                    System.out.println("End date: " + session.getEndDate().toString());
            }
        }
    }

    private void createNewSession() {
        System.out.println("Título da sessão:");
        String descritor = scanner.nextLine();
        if (sessionController.createNewSession(descritor)) {
            System.out.println("Session created successfully.");
        } else {
            System.out.println("Failed to create session. Ensure you are logged in as an admin.");
        }
    }

    private void displaySessionMenu() {
        boolean running = true;

        while (running) {
            System.out.println("Menu of Session: " + stateController.getCurrentSession().getDescritor());
            System.out.println("1. Manage Submissions");
            System.out.println("2. Update session descriptor");
            System.out.println("3. Update start date");
            System.out.println("4. Update end date");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    manageSubmissions();
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
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void updateSessionDescritor() {
        System.out.println("Session descriptor: " + stateController.getCurrentSession().getDescritor());
        System.out.print("Enter new descriptor: ");
        String newDescriptor = scanner.nextLine().trim();
        if (sessionController.updateSessionDescritor(newDescriptor)) {
            System.out.println("Session descriptor updated.");
        }
    }


    private void updateSessionStartDate() {
        System.out.println("Session descriptor: " + stateController.getCurrentSession().getDescritor());
        System.out.print("Enter new start date (dd/MM/yyyy): ");
        String newStartDateStr = scanner.nextLine().trim();
        try {
            Date newStartDate = new SimpleDateFormat("dd/MM/yyyy").parse(newStartDateStr);
            if (sessionController.updateSessionStartDate(newStartDate)) {
                System.out.println("Session start date updated.");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void updateSessionEndDate() {
        System.out.println("Session descriptor: " + stateController.getCurrentSession().getDescritor());
        System.out.print("Enter new end date (dd/MM/yyyy): ");
        String newEndDateStr = scanner.nextLine().trim();
        try {
            Date newEndDate = new SimpleDateFormat("dd/MM/yyyy").parse(newEndDateStr);
            if (sessionController.updateSessionEndDate(newEndDate)) {
                System.out.println("Session end date updated.");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void manageSubmissions() {
        // Implement manage submissions functionality
    }

    private void updateEventDescritor() {
        System.out.println("Event descriptor: " + stateController.getCurrentEvent().getDescritor());
        System.out.print("Enter new descriptor: ");
        String newDescriptor = scanner.nextLine().trim();
        if (eventController.updateEventDescritor(newDescriptor)) {
            System.out.println("Event descriptor updated.");
        }
    }

    private void updateEventDirector() {
        System.out.println("Event descriptor: " + stateController.getCurrentEvent().getDescritor());
        System.out.print("Enter the new director email: ");
        String newDirectorEmail = scanner.nextLine().trim();
        if (eventController.updateEventDirector(newDirectorEmail)) {
            System.out.println("Event director updated.");
        }
    }

    private void updateEventStartDate() {
        System.out.println("Event descriptor: " + stateController.getCurrentEvent().getDescritor());
        System.out.print("Enter new start date (dd/MM/yyyy): ");
        String newStartDateStr = scanner.nextLine().trim();
        try {
            Date newStartDate = new SimpleDateFormat("dd/MM/yyyy").parse(newStartDateStr);
            if (eventController.updateEventStartDate(newStartDate)) {
                System.out.println("Event start date updated.");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void updateEventEndDate() {
        System.out.println("Event descriptor: " + stateController.getCurrentEvent().getDescritor());
        System.out.print("Enter new end date (dd/MM/yyyy): ");
        String newEndDateStr = scanner.nextLine().trim();
        try {
            Date newEndDate = new SimpleDateFormat("dd/MM/yyyy").parse(newEndDateStr);
            if (eventController.updateEventEndDate(newEndDate)) {
                System.out.println("Event end date updated.");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void createNewEvent() {
        System.out.print("Enter event descriptor: ");
        String descriptor = scanner.nextLine().trim();
        System.out.print("Enter director email: ");
        String directorEmail = scanner.nextLine().trim();
        Date startDate, endDate;

        try {
            System.out.print("Enter start date (dd/MM/yyyy): ");
            String startDateStr = scanner.nextLine().trim();
            startDate = new SimpleDateFormat("dd/MM/yyyy").parse(startDateStr);

            System.out.print("Enter end date (dd/MM/yyyy): ");
            String endDateStr = scanner.nextLine().trim();
            endDate = new SimpleDateFormat("dd/MM/yyyy").parse(endDateStr);

            if (eventController.createNewEvent(descriptor, directorEmail, startDate, endDate)) {
                System.out.println("Event created!");
            } else {
                System.out.println("Unable to create a new event.");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void viewEventsByUser() {
        int counter = 1;
        for(GreatEvent event : eventController.getAllEventsByUser()){
            if (event != null) {
                System.out.println("index [ " + counter + " ]");
                System.out.println("Event: " + event.getDescritor());
                System.out.println("Director: " + event.getDirector());
                if(event.getStartDate() != null) System.out.println("Start Date: " + new SimpleDateFormat("yyyy-MM-dd").format(event.getStartDate()));
                if(event.getEndDate() != null) System.out.println("End Date: " + new SimpleDateFormat("yyyy-MM-dd").format(event.getEndDate()));
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
                    if(event.getUuid() != null) {
                        eventController.closeCurrentEvent();
                        eventController.changeCurrentEvent(event.getUuid());
                        displayEventMenu();
                        return;
                    }
                } else{
                    innerCount++;
                }
            }
        }
    }

    private void viewAllEvents() {
        int counter = 1;
        for(GreatEvent event : eventController.getAllEvents()){
            if (event != null) {
                System.out.println("Event: " + event.getDescritor());
                System.out.println("Director: " + event.getDirector());
                if(event.getStartDate() != null )System.out.println("Start Date: " + new SimpleDateFormat("yyyy-MM-dd").format(event.getStartDate()));
                System.out.println("End Date: " + new SimpleDateFormat("yyyy-MM-dd").format(event.getEndDate()));
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
                    displayEventMenuForCommomUser();
                    return;
                } else{
                    innerCount++;
                }
            }
        }
    }

    public void viewSubscriptions() {
        int counter = 1;
        for (Subscription subscription : stateController.getCurrentUser().getSubscriptions()) {
            if (subscription != null) {
                System.out.println("index [ " + counter + " ]");
                System.out.println("Event: " + crudController.sessionCRUD.returnSession(subscription.getSessionUuid()));
                if (subscription.getDate() != null)
                    System.out.println("Subscription date: " + subscription.getDate().toString());
                counter++;
            }
        }
    }
}
