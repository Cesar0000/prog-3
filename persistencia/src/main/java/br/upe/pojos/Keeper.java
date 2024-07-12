package br.upe.pojos;

public class Keeper implements KeeperInterface {
    public GreatEvent createGreatEvent(){
        return new GreatEvent();
    }
    public SubEvent createSubEvent(){
        return new SubEvent();
    }

    public CommomUser createCommomUser() {
        return new CommomUser();
    }

    public Subscription createSubscription(){
        return new Subscription();
    }
}
