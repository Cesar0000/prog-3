package br.upe.pojos;

import java.util.Collection;

public class Session {
    private Collection<Subscription> subscriptions;

    public void addSubscription(Subscription subscription){
        subscriptions.add(subscription);
    }

    public Collection<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Collection<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }
}
