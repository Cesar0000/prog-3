package br.upe.pojos;

import java.util.concurrent.Flow;

public interface KeeperInterface {
    GreatEvent createGreatEvent();
    SubEvent createSubEvent();
    CommomUser createCommomUser();
    Subscription createSubscription();
}
