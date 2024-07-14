package br.upe.pojos;

import java.util.Collection;
import java.util.UUID;

public class Session extends EventComponent {
    private Collection<Subscription> subscriptions;

    public void setDescritor(String descritor) {
        this.descritor = descritor;
    }
    public String getDescritor(){
        return this.descritor;
    }
    public void addSubscription(Subscription subscription){
        subscriptions.add(subscription);
    }
    public Collection<Subscription> getSubscriptions() {
        return subscriptions;
    }
    public void setSubscriptions(Collection<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
    public UUID getUuid() {
        return this.uuid;
    }
    public UUID getEventUuid() {
        return eventUuid;
    }
    public void setEventUuid(UUID eventUuid) {
        this.eventUuid = eventUuid;
    }
}
