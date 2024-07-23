package br.upe.operations;

import br.upe.pojos.Submission;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class SubmissionCRUDTest {
    private static final Logger logger = LoggerFactory.getLogger(SubmissionCRUDTest.class);
    private SubmissionCRUD submissionCRUD;

    @BeforeAll
    public static void clearFiles() {
        try (BufferedWriter buffer = new BufferedWriter(new FileWriter(".\\state\\submissions.csv"))) {
            buffer.write(""); // Limpa o conteúdo do arquivo submissions.csv
        } catch (IOException e) {
            logger.error("Erro ao limpar arquivo de submissões: ", e);
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

        // verificando se a submissão foi escrita corretamente
        try (BufferedReader buffer = new BufferedReader(new FileReader(".\\state\\submissions.csv"))) {
            String line;
            while ((line = buffer.readLine()) != null) {
                logger.debug("Linha no arquivo: {}", line);
            }
        } catch (IOException e) {
            logger.error("Erro ao ler arquivo de submissões: ", e);
        }

        // recupera a submissão
        Submission retrievedSubmission = SubmissionCRUD.returnSubmission(submissionUuid);
        assertNotNull(retrievedSubmission, "A submissão retornada não deve ser nula.");
        assertEquals(submission.getUuid(), retrievedSubmission.getUuid(), "O UUID da submissão deve ser igual ao esperado.");
        assertEquals(submission.getEventUuid(), retrievedSubmission.getEventUuid(), "O UUID do evento deve ser igual ao esperado.");
        assertEquals(submission.getUserUuid(), retrievedSubmission.getUserUuid(), "O UUID do usuário deve ser igual ao esperado.");
        assertEquals(submission.getDate(), retrievedSubmission.getDate(), "A data deve ser igual ao esperado.");
    }
}
