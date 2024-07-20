package br.upe.controllers;

public class TestController {
    public static void main(String[] args){
        StateController state = new StateController();
        CRUDController crud = new CRUDController();

        AuthController auth = new AuthController(state, crud);

        auth.createNewAdmin("italan.leal@upe.br", "password");
        auth.login("italan.leal@upe.br", "password");

        System.out.println(state.getCurrentUser());
        auth.logout();
        System.out.println(state.getCurrentUser());
    }
}
