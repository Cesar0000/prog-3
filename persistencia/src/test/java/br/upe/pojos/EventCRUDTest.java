package br.upe.operations;

import br.upe.pojos.GreatEvent;
import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.*;
import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EventCRUDTest {
    private EventCRUD eventCRUD;
    private final String filePath = ".\\state\\events.csv";

    @BeforeEach
    public void setUp() {
        eventCRUD = new EventCRUD();
        // Limpar o arquivo de estado antes de cada teste
        try {
            new FileWriter(filePath, false).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateEvent() throws IOException {
        GreatEvent event = new GreatEvent();
        event.setUuid(UUID.randomUUID());
        event.setDescritor("Test Event");
        event.setDirector("Director Name");
        event.setStartDate(Date.from(Instant.now()));
        event.setEndDate(Date.from(Instant.now().plusSeconds(3600)));
        event.setSessions(new ArrayList<>());
        event.setSubmissions(new ArrayList<>());

        eventCRUD.createEvent(event);

        List<String> lines = Files.readAllLines(Paths.get(filePath));
        assertEquals(1, lines.size());
        assertTrue(lines.get(0).contains(event.getUuid().toString()));
    }

    @Test
    public void testDeleteEvent() throws IOException {
        UUID uuidToDelete = UUID.randomUUID();
        GreatEvent event1 = new GreatEvent();
        event1.setUuid(uuidToDelete);
        event1.setDescritor("Event 1");
        event1.setDirector("Director 1");
        event1.setStartDate(Date.from(Instant.now()));
        event1.setEndDate(Date.from(Instant.now().plusSeconds(3600)));
        event1.setSessions(new ArrayList<>());
        event1.setSubmissions(new ArrayList<>());

        GreatEvent event2 = new GreatEvent();
        event2.setUuid(UUID.randomUUID());
        event2.setDescritor("Event 2");
        event2.setDirector("Director 2");
        event2.setStartDate(Date.from(Instant.now()));
        event2.setEndDate(Date.from(Instant.now().plusSeconds(3600)));
        event2.setSessions(new ArrayList<>());
        event2.setSubmissions(new ArrayList<>());

        eventCRUD.createEvent(event1);
        eventCRUD.createEvent(event2);

        eventCRUD.deleteEvent(uuidToDelete);

        List<String> lines = Files.readAllLines(Paths.get(filePath));
        assertEquals(1, lines.size());
        assertFalse(lines.get(0).contains(uuidToDelete.toString()));
    }

    @Test
    public void testUpdateEvent() throws IOException {
        UUID uuidToUpdate = UUID.randomUUID();
        GreatEvent original = new GreatEvent();
        original.setUuid(uuidToUpdate);
        original.setDescritor("Old Event");
        original.setDirector("Old Director");
        original.setStartDate(Date.from(Instant.now()));
        original.setEndDate(Date.from(Instant.now().plusSeconds(3600)));
        original.setSessions(new ArrayList<>());
        original.setSubmissions(new ArrayList<>());

        GreatEvent updated = new GreatEvent();
        updated.setUuid(uuidToUpdate);
        updated.setDescritor("Updated Event");
        updated.setDirector("Updated Director");
        updated.setStartDate(Date.from(Instant.now()));
        updated.setEndDate(Date.from(Instant.now().plusSeconds(7200)));
        updated.setSessions(new ArrayList<>());
        updated.setSubmissions(new ArrayList<>());

        eventCRUD.createEvent(original);
        eventCRUD.updateEvent(uuidToUpdate, updated);

        List<String> lines = Files.readAllLines(Paths.get(filePath));
        assertEquals(1, lines.size());
        assertTrue(lines.get(0).contains(updated.getDescritor()));
    }

    @Test
    public void testReturnEventByUuid() {
        UUID uuidToReturn = UUID.randomUUID();
        GreatEvent event = new GreatEvent();
        event.setUuid(uuidToReturn);
        event.setDescritor("Event for Return Test");
        event.setDirector("Director Name");
        event.setStartDate(Date.from(Instant.now()));
        event.setEndDate(Date.from(Instant.now().plusSeconds(3600)));
        event.setSessions(new ArrayList<>());
        event.setSubmissions(new ArrayList<>());

        eventCRUD.createEvent(event);

        GreatEvent returnedEvent = EventCRUD.returnEvent(uuidToReturn);
        assertNotNull(returnedEvent);
        assertEquals(uuidToReturn, returnedEvent.getUuid());
    }
}
