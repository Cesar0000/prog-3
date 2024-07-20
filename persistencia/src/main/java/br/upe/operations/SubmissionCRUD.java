package br.upe.operations;

import br.upe.pojos.HelperInterface;
import br.upe.pojos.Submission;
import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubmissionCRUD extends ClassCRUD {
    public SubmissionCRUD() { super(); }

    public void createSubmission(Submission submission){
        try(BufferedWriter buffer = new BufferedWriter(new FileWriter(".\\state\\submissions.csv", true))){
            buffer.write(submission.getUuid().toString() + ";");
            buffer.write( submission.getEventUuid().toString() + ";");
            buffer.write( submission.getUserUuid().toString() + ";");
            buffer.write( submission.getDate().toInstant().toString() + ";");

            buffer.newLine();
        } catch (Exception e) {}
    }
    public void deleteSubmission(UUID submissionUuid){
        ArrayList<String> fileCopy = new ArrayList<>();

        try(BufferedReader buffer = new BufferedReader(new FileReader(".\\state\\submissions.csv"))){
            while(buffer.ready()){
                fileCopy.add(buffer.readLine());
            };
        } catch (Exception e) {}

        try(BufferedWriter buffer = new BufferedWriter(new FileWriter(".\\state\\submissions.csv"))){
            for(String line: fileCopy){
                if(line.contains(submissionUuid.toString())) continue;
                buffer.write(line);
                buffer.newLine();
            }
        } catch (Exception e) {}
    }
    public void updateSubmission(UUID submissionUuid, Submission source){
        Submission submission = returnSubmission(submissionUuid);
        deleteSubmission(submission.getUuid());
        HelperInterface.checkout(source, submission);
        createSubmission(submission);
    }

    public static Submission returnSubmission(UUID submissionUuid){
        String rawSubmission = "";

        try(BufferedReader buffer = new BufferedReader(new FileReader(".\\state\\subscriptions.csv"))){
            while(buffer.ready()){
                String line = buffer.readLine();
                if(line.contains(submissionUuid.toString())) {
                    rawSubmission  = line;
                    break;
                }
            };
        } catch (Exception e) {}

        if(rawSubmission.isEmpty()) return null;

        Submission newSubmission = new Submission();
        Pattern pattern = Pattern.compile("(.*)(;)(.*)(;)(.*)(;)(.*)(;)");
        Matcher matcher = pattern.matcher(rawSubmission);

        if(matcher.matches()){
            newSubmission.setUuid(UUID.fromString(matcher.group(1)));
            newSubmission.setEventUuid(UUID.fromString(matcher.group(3)));
            newSubmission.setUserUuid(UUID.fromString(matcher.group(5)));
            newSubmission.setDate(Date.from(Instant.parse(matcher.group(7))));
        }

        return newSubmission;
    }
}
