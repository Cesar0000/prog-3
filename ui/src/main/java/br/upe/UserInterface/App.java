package br.upe.UserInterface;

import br.upe.controllers.AuthController;
import br.upe.controllers.StateController;
import br.upe.controllers.UserController;
import br.upe.controllers.CRUDController;

import java.util.Scanner;

public class App {
    private final AuthController authController;
    private final UserController userController;
    private final Scanner scanner=new Scanner(System.in);
    private boolean isLoggedIn=false;

    public App(AuthController authController,UserController userController) {
        this.authController=authController;
        this.userController=userController;
    }

    public void runMenu() {
        boolean running=true;

        while(running) {
            if(!isLoggedIn) {
                System.out.println("Menu:");
                System.out.println("1.Cadastrar novo usuário");
                System.out.println("2.Cadastrar novo administrador");
                System.out.println("3.Login");
                System.out.println("4.Sair");
                System.out.print("Escolha uma opção: ");

                int choice=scanner.nextInt();
                scanner.nextLine();

                switch(choice) {
                    case 1:
                        createUser();
                        break;
                    case 2:
                        createAdmin();
                        break;
                    case 3:
                        login();
                        break;
                    case 4:
                        running=false;
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } else {
                System.out.println("Menu:");
                System.out.println("5.Atualizar nome");
                System.out.println("6.Atualizar email");
                System.out.println("7.Atualizar senha");
                System.out.println("8.Logout");
                System.out.println("9.Sair");
                System.out.print("Escolha uma opção: ");

                int choice=scanner.nextInt();
                scanner.nextLine();

                switch(choice) {
                    case 5:
                        updateUserName();
                        break;
                    case 6:
                        updateUserEmail();
                        break;
                    case 7:
                        updateUserPassword();
                        break;
                    case 8:
                        logout();
                        break;
                    case 9:
                        running=false;
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            }
        }

        System.out.println("Aplicação encerrada.");
    }

    private void createUser() {
        System.out.print("Digite o email do novo usuário: ");
        String email=scanner.nextLine();
        System.out.print("Digite a senha do novo usuário: ");
        String password=scanner.nextLine();
        if(authController.createNewUser(email,password)) {
            System.out.println("Usuário criado com sucesso.");
        } else {
            System.out.println("Falha ao criar usuário. Email já está em uso.");
        }
    }

    private void createAdmin() {
        System.out.print("Digite o email do novo admin: ");
        String email=scanner.nextLine();
        System.out.print("Digite a senha do novo admin: ");
        String password=scanner.nextLine();
        if(authController.createNewAdmin(email,password)) {
            System.out.println("Admin criado com sucesso.");
        } else {
            System.out.println("Falha ao criar admin. Email já está em uso.");
        }
    }

    private void login() {
        System.out.print("Digite seu email: ");
        String email=scanner.nextLine();
        System.out.print("Digite sua senha: ");
        String password=scanner.nextLine();
        if(authController.login(email,password)) {
            isLoggedIn=true;
            System.out.println("Login bem-sucedido.");
        } else {
            System.out.println("Falha no login. Verifique seu email e senha.");
        }
    }

    private void logout() {
        if(authController.logout()) {
            isLoggedIn=false;
            System.out.println("Logout bem-sucedido.");
        } else {
            System.out.println("Falha ao fazer logout.");
        }
    }

    private void updateUserName() {
        System.out.print("Digite o novo nome: ");
        String newName=scanner.nextLine();
        userController.updateUserName(newName);
        System.out.println("Nome atualizado com sucesso.");
    }

    private void updateUserEmail() {
        System.out.print("Digite o novo email: ");
        String newEmail=scanner.nextLine();
        userController.updateUserEmail(newEmail);
        System.out.println("Email atualizado com sucesso.");
    }

    private void updateUserPassword() {
        System.out.print("Digite a nova senha: ");
        String newPassword=scanner.nextLine();
        userController.updateUserPassword(newPassword);
        System.out.println("Senha atualizada com sucesso.");
    }

    public static void main(String[] args) {
        StateController stateController=new StateController();
        CRUDController crudController=new CRUDController();
        AuthController authController=new AuthController(stateController,crudController);
        UserController userController=new UserController(stateController,crudController);
        App app=new App(authController,userController);
        app.runMenu();
    }
}
