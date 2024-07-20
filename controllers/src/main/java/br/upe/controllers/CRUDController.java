package br.upe.controllers;

import br.upe.operations.*;

public class CRUDController {
    public final EventCRUD eventCRUD = InterfacesForCRUD.newEventCRUD();
    public final SessionCRUD sessionCRUD = InterfacesForCRUD.newSessionCRUD();
    public final SubmissionCRUD submissionCRUD = InterfacesForCRUD.newSubmissionCRUD();
    public final SubscriptionCRUD subscriptionCRUD = InterfacesForCRUD.newSubscriptionCRUD();
    public final UserCRUD userCRUD = InterfacesForCRUD.newUserCRUD();
}
