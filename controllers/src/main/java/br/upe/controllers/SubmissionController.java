package br.upe.controllers;

import br.upe.pojos.GreatEvent;
import br.upe.pojos.KeeperInterface;
import br.upe.pojos.Submission;
import br.upe.pojos.Subscription;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;


public class SubmissionController {
    private CRUDController crudController;
    private StateController stateController;

    public SubmissionController(StateController stateController, CRUDController crudController) {
        this.stateController = stateController;
        this.crudController = crudController;
    }

    private Collection<Submission> getAllSubmissions() {
        return crudController.submissionCRUD.returnSubmission();
    }

    public Collection<Submission> getAllSubmissionsByUser(UUID userUuid) {
        Collection<Submission> submissions = getAllSubmissions();
        return submissions.stream()
                .filter(submission -> submission.getUserUuid().equals(userUuid))
                .collect(Collectors.toList());
    }

    public Collection<Submission> getAllSubmissionsByEvent(UUID eventUuid) {
        Collection<Submission> submissions = getAllSubmissions();
        return submissions.stream()
                .filter(submission -> submission.getUserUuid().equals(eventUuid))
                .collect(Collectors.toList());
    }

    public void removeSubmission(UUID submissionUuid) {
        Submission submission = crudController.submissionCRUD.returnSubmission(submissionUuid);
        GreatEvent event = crudController.eventCRUD.returnEvent(submission.getEventUuid());

        if(event == null){
            return;
        }

        for(Submission submission1 : event.getSubmissions()){
            if(submission1.getUuid().equals(submissionUuid)){
                event.getSubmissions().remove(submission);
            }
        }

        GreatEvent eventHandler = KeeperInterface.createGreatEvent();
        eventHandler.setSubmissions(event.getSubmissions());

        crudController.submissionCRUD.deleteSubmission(submission.getUuid());
        crudController.eventCRUD.updateEvent(event.getUuid(), eventHandler);
    }
}
