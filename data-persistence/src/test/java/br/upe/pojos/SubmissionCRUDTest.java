package br.upe.operations;

import br.upe.pojos.Submission;
import org.junit.jupiter.api.*;
import java.io.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class SubmissionCRUDTest {

    private SubmissionCRUD submissionCRUD;

    @BeforeAll
    public static void clearFiles() {
        try(BufferedWriter buffer = new BufferedWriter(new FileWriter(".\\state\\submissions.csv"))) {
            buffer.write("");
        } catch(IOException e) {
            System.out.println("Erro ao limpar arquivo de submissões: " + e.getMessage());
        }
    }

    @BeforeEach
    public void setUp() {
        submissionCRUD = new SubmissionCRUD();
    }

    @Test
    public void testCreateSubmission() {
        Submission submission = new Submission();
        UUID submissionUuid = UUID.randomUUID();
        submission.setUuid(submissionUuid);
        submission.setEventUuid(UUID.randomUUID());
        submission.setUserUuid(UUID.randomUUID());
        submission.setDate(new Date());
        submissionCRUD.createSubmission(submission);

        Submission retrievedSubmission = SubmissionCRUD.returnSubmission(submissionUuid);
        assertNotNull(retrievedSubmission, "A submissão retornada não deve ser nula.");
        assertEquals(submission.getUuid(), retrievedSubmission.getUuid(), "O UUID da submissão deve ser igual ao esperado.");
        assertEquals(submission.getEventUuid(), retrievedSubmission.getEventUuid(), "O UUID do evento deve ser igual ao esperado.");
        assertEquals(submission.getUserUuid(), retrievedSubmission.getUserUuid(), "O UUID do usuário deve ser igual ao esperado.");
        assertEquals(submission.getDate(), retrievedSubmission.getDate(), "A data deve ser igual ao esperado.");
    }

    @Test
    public void testUpdateSubmission() {
        Submission submission = new Submission();
        UUID submissionUuid = UUID.randomUUID();
        submission.setUuid(submissionUuid);
        submission.setEventUuid(UUID.randomUUID());
        submission.setUserUuid(UUID.randomUUID());
        submission.setDate(new Date());
        submissionCRUD.createSubmission(submission);

        // Atualiza a submissão com novos dados
        Submission updatedSubmission = new Submission();
        updatedSubmission.setUuid(submissionUuid);
        updatedSubmission.setEventUuid(UUID.randomUUID()); // Novo UUID para o evento
        updatedSubmission.setUserUuid(UUID.randomUUID()); // Novo UUID para o usuário
        updatedSubmission.setDate(new Date(System.currentTimeMillis() + 1000000)); // Atualiza a data

        submissionCRUD.updateSubmission(submissionUuid, updatedSubmission);

        Submission retrievedSubmission = SubmissionCRUD.returnSubmission(submissionUuid);
        assertNotNull(retrievedSubmission, "A submissão retornada não deve ser nula.");
        assertEquals(updatedSubmission.getUuid(), retrievedSubmission.getUuid(), "O UUID da submissão deve ser igual ao esperado.");
        assertEquals(updatedSubmission.getEventUuid(), retrievedSubmission.getEventUuid(), "O UUID do evento deve ser igual ao esperado.");
        assertEquals(updatedSubmission.getUserUuid(), retrievedSubmission.getUserUuid(), "O UUID do usuário deve ser igual ao esperado.");
        assertEquals(updatedSubmission.getDate(), retrievedSubmission.getDate(), "A data atualizada deve ser igual à esperada.");
    }

    @Test
    public void testDeleteSubmission() {
        Submission submission = new Submission();
        UUID submissionUuid = UUID.randomUUID();
        submission.setUuid(submissionUuid);
        submission.setEventUuid(UUID.randomUUID());
        submission.setUserUuid(UUID.randomUUID());
        submission.setDate(new Date());
        submissionCRUD.createSubmission(submission);

        submissionCRUD.deleteSubmission(submissionUuid);
        Submission removedSubmission = SubmissionCRUD.returnSubmission(submissionUuid);
        assertNull(removedSubmission, "A submissão removida deve ser nula.");
    }

    @Test
    public void testReturnSubmission() {

        Submission submission = new Submission();
        UUID submissionUuid = UUID.randomUUID();
        submission.setUuid(submissionUuid);
        submission.setEventUuid(UUID.randomUUID());
        submission.setUserUuid(UUID.randomUUID());
        submission.setDate(new Date());
        submissionCRUD.createSubmission(submission);


        Submission retrievedSubmission = SubmissionCRUD.returnSubmission(submissionUuid);


        assertNotNull(retrievedSubmission, "A submissão retornada não deve ser nula.");
        assertEquals(submission.getUuid(), retrievedSubmission.getUuid(), "O UUID da submissão deve ser igual ao esperado.");
        assertEquals(submission.getEventUuid(), retrievedSubmission.getEventUuid(), "O UUID do evento deve ser igual ao esperado.");
        assertEquals(submission.getUserUuid(), retrievedSubmission.getUserUuid(), "O UUID do usuário deve ser igual ao esperado.");
        assertEquals(submission.getDate(), retrievedSubmission.getDate(), "A data deve ser igual ao esperado.");
    }
}
