package br.upe.operations;

import br.upe.pojos.GreatEvent;
import org.junit.jupiter.api.*;

import java.io.*;
import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class EventCRUDTest {

//    private static final String FILE_PATH = ".\\state\\events.csv";
//
//    @BeforeEach
//    public void setUp() throws IOException {
//        // Prepare the file for testing, ensure it starts empty
//        new File(FILE_PATH).createNewFile();
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
//            writer.write("");
//        }
//    }
//
//    @Test
//    public void testCreateEvent() throws IOException {
//        EventCRUD eventCRUD = new EventCRUD();
//        GreatEvent event = new GreatEvent();
//        UUID uuid = UUID.randomUUID();
//        event.setUuid(uuid);
//        event.setDescritor("Test Event");
//        event.setDirector("Test Director");
//        event.setStartDate(Date.from(Instant.now()));
//        event.setEndDate(Date.from(Instant.now()));
//        event.setSessions(new ArrayList<>());
//        event.setSubmissions(new ArrayList<>());
//
//        eventCRUD.createEvent(event);
//
//        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
//            String line = reader.readLine();
//            assertNotNull(line);
//            assertTrue(line.contains(uuid.toString()));
//        }
//    }
//
//    @Test
//    public void testDeleteEvent() throws IOException {
//        EventCRUD eventCRUD = new EventCRUD();
//        GreatEvent event = new GreatEvent();
//        UUID uuid = UUID.randomUUID();
//        event.setUuid(uuid);
//        event.setDescritor("Test Event");
//        event.setDirector("Test Director");
//        event.setStartDate(Date.from(Instant.now()));
//        event.setEndDate(Date.from(Instant.now()));
//        event.setSessions(new ArrayList<>());
//        event.setSubmissions(new ArrayList<>());
//
//        eventCRUD.createEvent(event);
//        eventCRUD.deleteEvent(uuid);
//
//        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                assertFalse(line.contains(uuid.toString()));
//            }
//        }
//    }
//
//    @Test
//    public void testUpdateEvent() {
//        EventCRUD eventCRUD = new EventCRUD();
//        GreatEvent event = new GreatEvent();
//        UUID uuid = UUID.randomUUID();
//        event.setUuid(uuid);
//        event.setDescritor("Test Event");
//        event.setDirector("Test Director");
//        event.setStartDate(Date.from(Instant.now()));
//        event.setEndDate(Date.from(Instant.now()));
//        event.setSessions(new ArrayList<>());
//        event.setSubmissions(new ArrayList<>());
//
//        eventCRUD.createEvent(event);
//
//        GreatEvent updatedEvent = new GreatEvent();
//        updatedEvent.setUuid(uuid);
//        updatedEvent.setDescritor("Updated Event");
//        updatedEvent.setDirector("Updated Director");
//        updatedEvent.setStartDate(Date.from(Instant.now()));
//        updatedEvent.setEndDate(Date.from(Instant.now()));
//        updatedEvent.setSessions(new ArrayList<>());
//        updatedEvent.setSubmissions(new ArrayList<>());
//
//        eventCRUD.updateEvent(uuid, updatedEvent);
//
//        GreatEvent returnedEvent = EventCRUD.returnEvent(uuid);
//        assertNotNull(returnedEvent);
//        assertEquals("Updated Event", returnedEvent.getDescritor());
//        assertEquals("Updated Director", returnedEvent.getDirector());
//    }
//
//    @Test
//    public void testReturnEvent() {
//        EventCRUD eventCRUD = new EventCRUD();
//        GreatEvent event = new GreatEvent();
//        UUID uuid = UUID.randomUUID();
//        event.setUuid(uuid);
//        event.setDescritor("Test Event");
//        event.setDirector("Test Director");
//        event.setStartDate(Date.from(Instant.now()));
//        event.setEndDate(Date.from(Instant.now()));
//        event.setSessions(new ArrayList<>());
//        event.setSubmissions(new ArrayList<>());
//
//        eventCRUD.createEvent(event);
//
//        GreatEvent returnedEvent = EventCRUD.returnEvent(uuid);
//        assertNotNull(returnedEvent);
//        assertEquals(uuid, returnedEvent.getUuid());
//        assertEquals("Test Event", returnedEvent.getDescritor());
//        assertEquals("Test Director", returnedEvent.getDirector());
//    }
}
