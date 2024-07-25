package br.upe.pojos;

public interface KeeperInterface {
    static GreatEvent createGreatEvent(){
        return new GreatEvent();
    }
    static CommomUser createCommomUser() {
       return new CommomUser();
    }
    static AdminUser createAdminUser() {
        return new AdminUser();
    }
    static Session createSession() {
        return new Session();
    }
    static Subscription createSubscription(){
        return new Subscription();
    }
    static Submission createSubmission() {
        return new Submission();
    }
}
