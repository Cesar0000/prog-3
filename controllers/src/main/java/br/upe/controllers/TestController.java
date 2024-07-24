package br.upe.controllers;

import br.upe.pojos.User;

import java.util.Collection;

public class TestController {
    public static void main(String[] args){
        StateController state = new StateController();
        CRUDController crud = new CRUDController();

        AuthController auth = new AuthController(state, crud);

        auth.createNewAdmin("italan.leal@upe.br", "password");
        auth.createNewUser("iaa.@uc.br", "senhas");
        auth.login("italan.leal@upe.br", "password");

        System.out.println(state.getCurrentUser());
        auth.logout();
        System.out.println(state.getCurrentUser());

        Collection<User> users = crud.userCRUD.returnUser();
        for(User user: users){
            System.out.println(user.getPassword());
        }
    }
}
