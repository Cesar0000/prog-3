package br.upe.controllers;

import br.upe.controllers.EventController;
import br.upe.pojos.AdminUser;
import br.upe.pojos.GreatEvent;
import br.upe.pojos.Submission;
import br.upe.pojos.User;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

public class EventControllerTest {

    private EventController eventController;
    private StateController stateController;
    private CRUDController crudController;

    @Before
    public void setUp() {
        StateController state = new StateController();
        CRUDController crud = new CRUDController();

        AuthController auth = new AuthController(state, crud);
        EventController event = new EventController(state, crud);
        SubmissionController sub = new SubmissionController(state, crud);
    }

    @Test
    public void testCreateNewEvent() {
        StateController state = new StateController();
        CRUDController crud = new CRUDController();
        AuthController auth = new AuthController(state, crud);
        EventController event = new EventController(state, crud);

        auth.createNewAdmin("carlos.winicius@upe.br", "apassowrd");
        auth.login("carlos.winicius@upe.br", "apassowrd");
        boolean result = event.createNewEvent("EVENTO 1", "JAckinho");
        auth.logout();

        assertTrue(result);
        assertNotNull(state.getCurrentEvent());
        assertNotNull(crud.eventCRUD.returnEvent(state.getCurrentEvent().getUuid()));
    }

    // atualizar o diretor tem a mesma formatação de atualizar o descritor
    @Test
    public void testUpdateEventDescritor() {

        StateController state = new StateController();
        CRUDController crud = new CRUDController();
        AuthController auth = new AuthController(state, crud);
        EventController event = new EventController(state, crud);

        auth.createNewAdmin("carlos.winicius@upe.br", "apassowrd");
        auth.login("carlos.winicius@upe.br", "apassowrd");

        boolean createResult = event.createNewEvent("Original Event", "JAckinho");
        assertTrue(createResult);
        assertNotNull(state.getCurrentEvent());

        GreatEvent createdEvent = crud.eventCRUD.returnEvent(state.getCurrentEvent().getUuid());
        assertNotNull(createdEvent);
        assertEquals("Original Event", createdEvent.getDescritor());

        event.updateEventDescritor("ATUALIZADO Event Descritor");

        GreatEvent updatedEvent = crud.eventCRUD.returnEvent(state.getCurrentEvent().getUuid());
        assertNotNull(updatedEvent);
        assertEquals("ATUALIZADO Event Descritor", updatedEvent.getDescritor());

        auth.logout();
    }

    @Test
    public void testUpdateEventStartDate() {
        StateController state = new StateController();
        CRUDController crud = new CRUDController();
        AuthController auth = new AuthController(state, crud);
        EventController event = new EventController(state, crud);

        auth.createNewAdmin("carlos.winicius@upe.br", "apassowrd");
        auth.login("carlos.winicius@upe.br", "apassowrd");
        event.createNewEvent("Original Event", "JAckinho");

        Date newStartDate = new Date();
        event.updateEventStartDate(newStartDate);

        GreatEvent updatedEvent = crud.eventCRUD.returnEvent(state.getCurrentEvent().getUuid());
        assertNotNull(updatedEvent);
        assertEquals(newStartDate, updatedEvent.getStartDate());

        auth.logout();
    }

    @Test
    public void testUpdateEventEndDate() {
        StateController state = new StateController();
        CRUDController crud = new CRUDController();
        AuthController auth = new AuthController(state, crud);
        EventController event = new EventController(state, crud);

        auth.createNewAdmin("carlos.winicius@upe.br", "apassowrd");
        auth.login("carlos.winicius@upe.br", "apassowrd");
        event.createNewEvent("Original Event", "JAckinho");

        Date newEndDate = new Date(new Date().getTime() + 10000000L);
        event.updateEventEndDate(newEndDate);

        GreatEvent updatedEvent = crud.eventCRUD.returnEvent(state.getCurrentEvent().getUuid());
        assertNotNull(updatedEvent);
        assertEquals(newEndDate, updatedEvent.getEndDate());

        auth.logout();
    }

    @Test
    public void testAddEventSubmission() {
        // Criar e autenticar um usuário comum
        StateController state = new StateController();
        CRUDController crud = new CRUDController();
        AuthController auth = new AuthController(state, crud);
        EventController event = new EventController(state, crud);

        auth.createNewAdmin("julio.mota@upe.br", "se");
        auth.login("julio.mota@upe.br", "se");
        event.createNewEvent("Evento Teste", "Diretor Teste");
        auth.logout();

        auth.createNewUser("marcelo.spacca@upe.br", "senha1");
        auth.login("marcelo.spacca@upe.br", "senha1");

        String submissionDescritor = "Algorítmo genético para análise de subgrupo";
        event.addEventSubmission(submissionDescritor);

        Collection<Submission> submissions = crud.submissionCRUD.returnSubmission();
        assertNotNull(submissions);
        assertTrue("A submissão deve estar presente na coleção de submissões.",
                submissions.stream()
                        .anyMatch(sub -> sub.getDescritor().equals(submissionDescritor) &&
                                sub.getUserUuid().equals(state.getCurrentUser().getUuid()) &&
                                sub.getEventUuid().equals(state.getCurrentEvent().getUuid())));

        GreatEvent eventRetrieved = crud.eventCRUD.returnEvent(state.getCurrentEvent().getUuid());
        assertNotNull(eventRetrieved);
        assertTrue("O evento deve conter a submissão adicionada.",
                eventRetrieved.getSubmissions().stream()
                        .anyMatch(sub -> sub.getDescritor().equals(submissionDescritor)));

        // fofinho deslogando
        auth.logout();
    }
    @Test
    public void changeCurrentEvent() {

        StateController state = new StateController();
        CRUDController crud = new CRUDController();
        AuthController auth = new AuthController(state, crud);
        EventController event = new EventController(state, crud);

        auth.createNewAdmin("jackinho@upe.br", "security");
        auth.login("jackinho@upe.br", "security");

        event.createNewEvent("Evento Teste1", "Diretor Teste2");
        GreatEvent createdEvent = crud.eventCRUD.returnEvent(state.getCurrentEvent().getUuid());
        UUID eventUuid = createdEvent.getUuid();

        event.changeCurrentEvent(eventUuid);

        assertNotNull(createdEvent);
        assertEquals(eventUuid, createdEvent.getUuid());
        assertEquals("Evento Teste1", createdEvent.getDescritor());
        assertEquals("Diretor Teste2",createdEvent.getDirector());

        auth.logout();
    }

    @Test
    public void testCloseCurrentEvent() {

        StateController state = new StateController();
        CRUDController crud = new CRUDController();
        AuthController auth = new AuthController(state, crud);
        EventController event = new EventController(state, crud);

        auth.createNewAdmin("jackinho@upe.br", "security");
        auth.login("jackinho@upe.br", "security");

        event.createNewEvent("Evento Teste1", "Diretor Teste2");
        GreatEvent createdEvent = crud.eventCRUD.returnEvent(state.getCurrentEvent().getUuid());
        UUID eventUuid = createdEvent.getUuid();

        event.changeCurrentEvent(eventUuid);

        assertNotNull(state.getCurrentEvent());
        assertEquals(eventUuid, state.getCurrentEvent().getUuid());

        event.closeCurrentEvent();

        assertNull(state.getCurrentEvent()); // Verifica se o evento atual foi fechado

        auth.logout();
    }

}
