package br.upe.pojos;

import br.upe.pojos.user.CommomUser;
import br.upe.pojos.user.User;

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
}
