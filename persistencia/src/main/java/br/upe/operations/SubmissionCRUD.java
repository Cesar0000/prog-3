package br.upe.operations;

import br.upe.pojos.HelperInterface;
import br.upe.pojos.Submission;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class SubmissionCRUD extends BaseCRUD {
    //private static final Logger logger = LoggerFactory.getLogger(SubmissionCRUD.class);

    public SubmissionCRUD() { super(); }

    public void createSubmission(Submission submission) {
        try (BufferedWriter buffer = new BufferedWriter(new FileWriter(".\\state\\submissions.csv", true))) {
            buffer.write(submission.getUuid().toString() + ";");
            buffer.write(submission.getEventUuid().toString() + ";");
            buffer.write(submission.getUserUuid().toString() + ";");
            buffer.write(submission.getDate().toInstant().toString() + ";");

            buffer.newLine();
        } catch (IOException e) {
            //logger.error("Erro ao criar submissão: ", e);
        }
    }

    public void deleteSubmission(UUID submissionUuid) {
        ArrayList<String> fileCopy = new ArrayList<>();

        try (BufferedReader buffer = new BufferedReader(new FileReader(".\\state\\submissions.csv"))) {
            while (buffer.ready()) {
                fileCopy.add(buffer.readLine());
            }
        } catch (IOException e) {
            //logger.error("Erro ao ler submissões: ", e);
        }

        try (BufferedWriter buffer = new BufferedWriter(new FileWriter(".\\state\\submissions.csv"))) {
            for (String line : fileCopy) {
                if (line.contains(submissionUuid.toString())) continue;
                buffer.write(line);
                buffer.newLine();
            }
        } catch (IOException e) {
            //logger.error("Erro ao deletar submissão: ", e);
        }
    }

    public void updateSubmission(UUID submissionUuid, Submission source) {
        Submission submission = returnSubmission(submissionUuid);
        deleteSubmission(submission.getUuid());
        HelperInterface.checkout(source, submission);
        createSubmission(submission);
    }

    public static Submission returnSubmission(UUID submissionUuid) {
        try (BufferedReader buffer = new BufferedReader(new FileReader(".\\state\\submissions.csv"))) {
            while (buffer.ready()) {
                String line = buffer.readLine();
                if (line.contains(submissionUuid.toString())) {
                    return ParserInterface.parseSubmission(line);
                }
            }
        } catch (IOException e) {}

        return null;
    }
    public static Collection<Submission> returnSubmission() {
        Collection<Submission> submissions = new ArrayList<>();

        try (BufferedReader buffer = new BufferedReader(new FileReader(".\\state\\submissions.csv"))) {
            while (buffer.ready()) {
                String line = buffer.readLine();
                if (!line.isEmpty()) {
                    submissions.add(ParserInterface.parseSubmission(line));
                }
            }
        } catch (IOException e) {}

        return submissions;
    }
}
