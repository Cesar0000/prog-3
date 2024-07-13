package br.upe.pojos;

import java.util.ArrayList;
import java.util.UUID;

public interface KeeperInterface {
    static GreatEvent createGreatEvent(){
        return new GreatEvent();
    }

    static CommomUser createCommomUser() {
        CommomUser commomUser = new CommomUser();
        commomUser.setSubscriptions(new ArrayList<Subscription>());
        return commomUser;
    }

    static Subscription createSubscription(User user){
        return new Subscription();
    }
}
