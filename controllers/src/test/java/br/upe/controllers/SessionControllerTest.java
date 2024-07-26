package br.upe.controllers;

import br.upe.operations.SessionCRUD;
import br.upe.pojos.*;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


public class SessionControllerTest {


    @Test
    public void testCreateNewSession() {
        StateController state = new StateController();
        CRUDController crud = new CRUDController();
        AuthController auth = new AuthController(state, crud);
        EventController event = new EventController(state, crud);
        SessionController session = new SessionController(state, crud);

        auth.createNewAdmin("carlos@upe.br", "apassowrda");
        auth.login("carlos@upe.br", "apassowrda");

        event.createNewEvent("EVENTO 5", "JAckinhos");

        String sessionDescritor = "SESSION 1";
        boolean sessionResult = session.createNewSession(sessionDescritor);
        auth.logout();

        assertTrue(sessionResult);
        assertNotNull(state.getCurrentSession());

        assertEquals(state.getCurrentEvent().getUuid(), state.getCurrentSession().getEventUuid());

        assertEquals(sessionDescritor, state.getCurrentSession().getDescritor());
    }

    @Test
    public void testUpdateSessionDescritor() {
        // Configuração inicial
        StateController state = new StateController();
        CRUDController crud = new CRUDController();
        AuthController auth = new AuthController(state, crud);
        EventController event = new EventController(state, crud);
        SessionController session = new SessionController(state, crud);


        auth.createNewAdmin("steragg@upe.br", "pwneed1");
        auth.login("steragg@upe.br", "pwneed1");

        event.createNewEvent("papo", "Diretor 1");
        session.createNewSession("Sessão 1");

        Session createdSession = crud.sessionCRUD.returnSession(state.getCurrentSession().getUuid());
        assertEquals("Sessão 1", createdSession.getDescritor());

        session.updateSessionDescritor("Sessão 1 Atualizada");

        Session updatedSession = crud.sessionCRUD.returnSession(state.getCurrentSession().getUuid());
        assertNotNull(updatedSession);
        assertEquals("Sessão 1 Atualizada", updatedSession.getDescritor());

        auth.logout();
    }
    @Test
    public void testAddSessionsSubscription() {

        StateController state = new StateController();
        CRUDController crud = new CRUDController();
        AuthController auth = new AuthController(state, crud);
        EventController event = new EventController(state, crud);
        SubscriptionController subscription = new SubscriptionController(state, crud);
        SessionController session = new SessionController(state, crud);

        auth.createNewAdmin("administrator.test@upe.br", "adminpassword");
        auth.login("adminitrator.test@upe.br", "adminpassword");

        Date eventStartDate = new Date();
        eventStartDate.setTime(eventStartDate.getTime() - 86400000L); // Data 1 dia atrás

        event.createNewEvent("testess", "Diretor 19");
        event.updateEventStartDate(eventStartDate);

        session.createNewSession("Sessão Teste");

        auth.logout();

        auth.createNewUser("user.test@upe.br", "userpassword");
        auth.login("user.test@upe.br", "userpassword");

        session.addSessionsSubscription();

        Session updatedSession = crud.sessionCRUD.returnSession(state.getCurrentSession().getUuid());
        assertNotNull(updatedSession, "A sessão retornada não deve ser nula.");
        assertTrue(updatedSession.getSubscriptions().stream()
                        .anyMatch(sub -> sub.getUserUuid().equals(state.getCurrentUser().getUuid()) &&
                                sub.getSessionUuid().equals(state.getCurrentSession().getUuid())),
                "A assinatura deve estar presente na sessão.");

        // Verificar se a assinatura foi associada ao usuário
        User currentUser = crud.userCRUD.returnUser(state.getCurrentUser().getUuid());
        assertNotNull(currentUser, "O usuário retornado não deve ser nulo.");
        assertTrue(currentUser.getSubscriptions().stream()
                        .anyMatch(sub -> sub.getSessionUuid().equals(state.getCurrentSession().getUuid())),
                "A assinatura deve estar associada ao usuário.");

        auth.logout();
    }
    @Test
    public void testChangeCurrentSession() {
        StateController state = new StateController();
        CRUDController crud = new CRUDController();
        AuthController auth = new AuthController(state, crud);
        EventController event = new EventController(state, crud);
        SessionController session = new SessionController(state, crud);

        // Criar e autenticar um administrador
        auth.createNewAdmin("admin1@example.com", "adminpassword1");
        auth.login("admin1@example.com", "adminpassword1");

        // Criar um evento e duas sessões
        boolean eventCreated = event.createNewEvent("Evento Teste 1", "Diretor Teste 1");
        assertTrue(eventCreated);

        Date startDate1 = new Date();
        Date endDate1 = new Date(startDate1.getTime() + 3600000L); // Uma hora depois
        boolean sessionCreated1 = session.createNewSession("Sessão Teste 1");
        assertTrue(sessionCreated1);

        Date startDate2 = new Date(startDate1.getTime() + 7200000L); // Duas horas depois
        Date endDate2 = new Date(startDate2.getTime() + 3600000L); // Uma hora depois
        boolean sessionCreated2 = session.createNewSession("Sessão Teste 2");
        assertTrue(sessionCreated2);

        // Mudar para a segunda sessão
        session.changeCurrentSession(state.getCurrentSession().getUuid());

        // Verificar se a sessão atual foi alterada
        Session currentSession = state.getCurrentSession();
        assertNotNull(currentSession);
        assertEquals("Sessão Teste 2", currentSession.getDescritor());

        // Logout do administrador
        auth.logout();
    }
    @Test
    public void testCloseCurrentSession() {
        StateController state = new StateController();
        CRUDController crud = new CRUDController();
        AuthController auth = new AuthController(state, crud);
        EventController event = new EventController(state, crud);
        SessionController session = new SessionController(state, crud);

        auth.createNewAdmin("admin2@example.com", "adminpassword2");
        auth.login("admin2@example.com", "adminpassword2");

        boolean eventCreated = event.createNewEvent("Evento Teste 2", "Diretor Teste 2");
        assertTrue(eventCreated);

        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() + 3600000L); // Uma hora depois
        boolean sessionCreated = session.createNewSession("Sessão Teste 3");
        assertTrue(sessionCreated);

        session.changeCurrentSession(state.getCurrentSession().getUuid());

        session.closeCurrentSession();

        assertNull(state.getCurrentSession());

        auth.logout();
    }


}
//   /* @Test
//    public void testUpdateSessionStartDate() {
//        StateController state = new StateController();
//        CRUDController crud = new CRUDController();
//        AuthController auth = new AuthController(state, crud);
//        EventController event = new EventController(state, crud);
//        SessionController session = new SessionController(state, crud);
//
//        // Criar e autenticar um administrador
//        auth.createNewAdmin("admin@example.com", "adminpassword");
//        auth.login("admin@example.com", "adminpassword");
//
//        // Criar um evento
//        Date eventStartDate = new Date(new Date().getTime() - 86400000L); // Data de início do evento um dia no passado
//        event.createNewEvent("Evento Teste", "Diretor Teste");
//        event.updateEventStartDate(eventStartDate);
//
//        // Criar uma sessão
//        session.createNewSession("Sessão Teste");
//        Date sessionStartDate = new Date(String.valueOf(state.getCurrentEvent().getStartDate().after(eventStartDate)));
//        // Atualizar a data de início da sessão
//        boolean updateResult = session.updateSessionStartDate(sessionStartDate);
//
//        // Recuperar a sessão atualizada
//        Session updatedSession = crud.sessionCRUD.returnSession(state.getCurrentSession().getUuid());
//
//        // Verificar se a atualização foi bem-sucedida
//        assertNotNull(updatedSession, "A sessão atualizada não deve ser nula.");
//        assertEquals(sessionStartDate, updatedSession.getStartDate(), "A data de início da sessão deve ser atualizada.");
//        assertTrue(updateResult, "A atualização da data de início da sessão deve ser bem-sucedida.");
//
//        // Logout do administrador
//        auth.logout();
//    }*/







//    @Test
//    public void testUpdateSessionStartDate() {
//        StateController state = new StateController();
//        CRUDController crud = new CRUDController();
//        AuthController auth = new AuthController(state, crud);
//        EventController event = new EventController(state, crud);
//        SessionController session = new SessionController(state, crud);
//
//        // Criação de um administrador e login
//        auth.createNewAdmin("cristovao@upe.br", "pwneed2");
//        auth.login("cristovao@upe.br", "pwneed2");
//
//        // Criar um evento
//        boolean eventCreated = event.createNewEvent("Evento 2", "Diretor 2");
//        assertTrue("O evento deve ser criado com sucesso.", eventCreated);
//
//        // Criar uma data de início inicial para a sessão
//        Date initialStartDate = new Date(new Date().getTime() - 10000000L); // 10 milhões de milissegundos no passado
//        boolean sessionCreated = session.createNewSession("Sessão 2", initialStartDate);
//        assertTrue("A sessão deve ser criada com sucesso.", sessionCreated);
//
//        // Definir nova data de início
//        Date newStartDate = new Date(new Date().getTime() + 10000000L); // 10 milhões de milissegundos no futuro
//        boolean updated = session.updateSessionStartDate(newStartDate);
//
//        // Verificar se a atualização foi bem-sucedida
//        assertTrue("A data de início da sessão deveria ter sido atualizada.", updated);
//
//        // Obter a sessão atualizada
//        Session updatedSession = crud.sessionCRUD.returnSession(state.getCurrentSession().getUuid());
//        assertNotNull("A sessão atualizada não deve ser nula.", updatedSession);
//
//        // Verificar se a data de início foi atualizada corretamente
//        assertEquals("A data de início da sessão não foi atualizada corretamente.", newStartDate, updatedSession.getStartDate());
//
//        auth.logout();
//    }

