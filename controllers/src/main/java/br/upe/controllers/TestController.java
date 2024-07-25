package br.upe.controllers;

import br.upe.operations.SubmissionCRUD;
import br.upe.pojos.AdminUser;
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
        EventController event = new EventController(state, crud);
        SubmissionController sub = new SubmissionController(state, crud);

        auth.createNewAdmin("italan.leal@upe.br", "password");
        auth.createNewUser("julio.mota@upe.br", "senhas");

        auth.login("italan.leal@upe.br", "password");

        event.createNewEvent("SUPER | 2024", "Tárcio");
        auth.logout();


        auth.login("julio.mota@upe.br", "senhas");
        event.addEventSubmission("Algorítimo genético para análise de subgrupo");

        Collection<Submission> sub1 = sub.getAllSubmissionsByUser(state.getCurrentUser().getUuid());
        sub.removeSubmission(sub1.iterator().next().getUuid());
    }
}
