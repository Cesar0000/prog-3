package br.upe.pojos;

import java.util.ArrayList;

public interface KeeperInterface {
    static GreatEvent createGreatEvent(){
        return new GreatEvent();
    }

    static CommomUser createCommomUser() {
        CommomUser commomUser = new CommomUser();
        commomUser.setSessionsHexCode(new ArrayList<String>());
        return commomUser;
    }

    static Subscription createSubscription(User user){
        return new Subscription();
    }
}
