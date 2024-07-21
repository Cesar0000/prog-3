package br.upe.controllers;

import br.upe.operations.*;

public class CRUDController {
    public final EventCRUD eventCRUD = CRUDInterface.newEventCRUD();
    public final SessionCRUD sessionCRUD = CRUDInterface.newSessionCRUD();
    public final SubmissionCRUD submissionCRUD = CRUDInterface.newSubmissionCRUD();
    public final SubscriptionCRUD subscriptionCRUD = CRUDInterface.newSubscriptionCRUD();
    public final UserCRUD userCRUD = CRUDInterface.newUserCRUD();
}
