package br.upe.controllers;

import br.upe.pojos.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {


    @Test
    public void testUpdateUserName() {

        StateController state = new StateController();
        CRUDController crud = new CRUDController();
        AuthController auth = new AuthController(state, crud);
        UserController user = new UserController(state, crud);

        auth.createNewAdmin("fernando@upe.br", "password15");
        auth.login("fernando@upe.br", "password15");

        user.updateUserName("fernandoétop");

        User updatedUser = crud.userCRUD.returnUser(state.getCurrentUser().getUuid());
        assertNotNull(updatedUser);
        assertEquals("fernandoétop", updatedUser.getName());

        auth.logout();
    }
    @Test
    public void testUpdateUserEmail() {

        StateController state = new StateController();
        CRUDController crud = new CRUDController();
        AuthController auth = new AuthController(state, crud);
        UserController user = new UserController(state, crud);


        auth.createNewAdmin("mirelemoutinho@upe.br", "senha24");
        auth.login("mirelemoutinho@upe.br", "senha24");

        user.updateUserEmail("jackinho@upe.br");

        User updatedUser = crud.userCRUD.returnUser(state.getCurrentUser().getUuid());
        assertNotNull(updatedUser);
        assertEquals("jackinho@upe.br", updatedUser.getEmail());

        auth.logout();
    }
    @Test
    public void testUpdateUserPassword() {

        StateController state = new StateController();
        CRUDController crud = new CRUDController();
        AuthController auth = new AuthController(state, crud);
        UserController user = new UserController(state, crud);

        auth.createNewAdmin("admin.bruce.wayne@upe.br", "securePass3!");
        auth.login("admin.bruce.wayne@upe.br", "securePass3!");

        user.updateUserPassword("BatMan@2024");

        User updatedUser = crud.userCRUD.returnUser(state.getCurrentUser().getUuid());
        assertNotNull(updatedUser);
        assertEquals("BatMan@2024", updatedUser.getPassword());

        auth.logout();
    }
}



