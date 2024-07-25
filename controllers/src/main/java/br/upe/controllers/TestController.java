package br.upe.controllers;

import br.upe.operations.SubmissionCRUD;
import br.upe.pojos.KeeperInterface;
import br.upe.pojos.Submission;
import br.upe.pojos.User;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

public class TestController {
    public static void main(String[] args){
        StateController state = new StateController();
        CRUDController crud = new CRUDController();

        AuthController auth = new AuthController(state, crud);

        auth.createNewAdmin("italan.leal@upe.br", "password");
        auth.createNewUser("iaa.@uc.br", "senhas");
        auth.login("italan.leal@upe.br", "password");
        Submission sub1 = KeeperInterface.createSubmission();

        sub1.setDescritor("Pipipi popopo");
        sub1.setDate(new Date());
        sub1.setUuid(UUID.randomUUID());

        crud.submissionCRUD.createSubmission(sub1);

        System.out.println(state.getCurrentUser());
        auth.logout();
        System.out.println(state.getCurrentUser());

        Collection<User> users = crud.userCRUD.returnUser();
        for(User user: users){
            System.out.println(user.getPassword());
        }
    }
}
