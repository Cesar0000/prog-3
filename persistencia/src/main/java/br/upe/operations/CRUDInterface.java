package br.upe.operations;

public interface CRUDInterface {
    static EventCRUD newEventCRUD() {
        return new EventCRUD();
    }
    static SessionCRUD newSessionCRUD() {
        return new SessionCRUD();
    }
    static SubmissionCRUD newSubmissionCRUD() {
        return new SubmissionCRUD();
    }
    static SubscriptionCRUD newSubscriptionCRUD() {
        return new SubscriptionCRUD();
    }
    static UserCRUD newUserCRUD() {
        return new UserCRUD();
    }
}
