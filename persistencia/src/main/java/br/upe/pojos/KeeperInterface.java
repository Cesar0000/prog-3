package br.upe.pojos;

import br.upe.pojos.user.CommomUser;

public interface KeeperInterface {
    public GreatEvent createGreatEvent();
    public SubEvent createSubEvent();
    public CommomUser createCommomUser();
}
