package br.upe.operations;

import br.upe.pojos.HelperInterface;
import br.upe.pojos.Submission;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;


public class SubmissionCRUD extends BaseCRUD {

    public SubmissionCRUD() { super(); }

    public void createSubmission(Submission submission) {
        try(BufferedWriter buffer = new BufferedWriter(new FileWriter(".\\state\\submissions.csv", true))) {
            buffer.write(ParserInterface.validadeString(submission.getUuid()) + ";");
            buffer.write(ParserInterface.validadeString(submission.getDescritor()) + ";");
            buffer.write(ParserInterface.validadeString(submission.getEventUuid()) + ";");
            buffer.write(ParserInterface.validadeString(submission.getUserUuid()) + ";");
            buffer.write((submission.getDate() != null ? ParserInterface.validadeString(submission.getDate().toInstant()): "") + ";");

            buffer.newLine();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteSubmission(UUID submissionUuid) {
        ArrayList<String> fileCopy = new ArrayList<>();

        try(BufferedReader buffer = new BufferedReader(new FileReader(".\\state\\submissions.csv"))) {
            while(buffer.ready()) {
                fileCopy.add(buffer.readLine());
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        try(BufferedWriter buffer = new BufferedWriter(new FileWriter(".\\state\\submissions.csv"))) {
            for (String line : fileCopy) {
                if (line.contains(submissionUuid.toString())) continue;
                buffer.write(line);
                buffer.newLine();
            }

        } catch(Exception e) {
            e.printStackTrace();

        }
    }

    public void updateSubmission(UUID submissionUuid, Submission source) {
        Submission existingSubmission = returnSubmission(submissionUuid);
        if (existingSubmission == null) {
            System.out.println("Submission not found for UUID: " + submissionUuid);
            return;
        }
        deleteSubmission(submissionUuid);
        HelperInterface.checkout(source, existingSubmission);
        createSubmission(existingSubmission);
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
                    Submission newSubmission = ParserInterface.parseSubmission(line);
                    if(newSubmission != null) submissions.add(newSubmission);
                }
            }
        } catch (IOException e) {}


        return submissions;
    }
}
