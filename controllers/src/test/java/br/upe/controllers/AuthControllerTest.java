package br.upe.controllers;

import br.upe.controllers.AuthController;
import br.upe.controllers.CRUDController;
import br.upe.controllers.StateController;
import br.upe.pojos.AdminUser;
import br.upe.pojos.User;
import br.upe.operations.HasherInterface;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AuthControllerTest {
    private AuthController authController;
    private StateController stateController;
    private CRUDController crudController;

    @Before
    public void setUp() {
        stateController = new StateController();
        crudController = new CRUDController();
        authController = new AuthController(stateController, crudController);
    }

    @Test
    public void testCreateNewUser_SuccessfulCreation() {
        // Arrange
        stateController = new StateController();
        crudController = new CRUDController();
        authController = new AuthController(stateController, crudController);
        String email = "test@example.com";
        String password = "password123";
        String hashedPassword = HasherInterface.hash(password);

        // Act
        boolean result = authController.createNewUser(email, password);
        authController.login(email, password); // Faz o login após a criação do usuário

        // Assert
        assertTrue(result);
        User user = (User) stateController.getCurrentUser(); // Casting para User
        assertNotNull(user);
        assertEquals(email, user.getEmail());
        assertEquals(hashedPassword, user.getPassword()); // Verifica se o hash armazenado é o correto
    }


    @Test
    public void testCreateNewUser_UserAlreadyExists() {
        // Arrange
        String email = "existing@example.com";
        String password = "password123";
        authController.createNewUser(email, password);

        // Act
        boolean result = authController.createNewUser(email, password);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testCreateNewAdmin_SuccessfulCreation() {
        stateController = new StateController();
        crudController = new CRUDController();
        authController = new AuthController(stateController, crudController);
        // Arrange
        String email = "admin@example.com";
        String password = "admin123";
        String hashedPassword = HasherInterface.hash(password);

        // Act
        boolean result = authController.createNewAdmin(email, password);
        authController.login(email, password);

        // Assert
        assertTrue(result);
        User admin = stateController.getCurrentUser();
        assertNotNull(admin);
        assertEquals(email, admin.getEmail());
        assertEquals(hashedPassword, admin.getPassword());
    }

    @Test
    public void testCreateNewAdmin_AdminAlreadyExists() {
        // Arrange
        String email = "existingadmin@example.com";
        String password = "admin123";
        authController.createNewAdmin(email, password);

        // Act
        boolean result = authController.createNewAdmin(email, password);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testLogin_SuccessfulLogin() {
        // Arrange
        String email = "test@example.com";
        String password = "password123";
        authController.createNewUser(email, password);

        // Act
        boolean result = authController.login(email, password);

        // Assert
        assertTrue(result);
        User user = (User) stateController.getCurrentUser();
        assertNotNull(user);
    }

    @Test
    public void testLogin_IncorrectPassword() {
        // Arrange
        String email = "test@example.com";
        String password = "password123";
        authController.createNewUser(email, password);

        // Act
        boolean result = authController.login(email, "incorrectpassword");

        // Assert
        assertFalse(result);
        assertNull(stateController.getCurrentUser());
    }

    @Test
    public void testLogin_UserDoesNotExist() {
        // Arrange
        String email = "nonexistent@example.com";
        String password = "password123";

        // Act
        boolean result = authController.login(email, password);

        // Assert
        assertFalse(result);
        assertNull(stateController.getCurrentUser());
    }

    @Test
    public void testLogout_SuccessfulLogout() {
        // Arrange
        String email = "test@example.com";
        String password = "password123";
        authController.createNewUser(email, password);
        authController.login(email, password);

        // Act
        boolean result = authController.logout();

        // Assert
        assertTrue(result);
        assertNull(stateController.getCurrentUser());
    }

    @Test
    public void testLogout_UserNotLoggedIn() {
        // Act
        boolean result = authController.logout();

        // Assert
        assertFalse(result);
        assertNull(stateController.getCurrentUser());
    }
}
