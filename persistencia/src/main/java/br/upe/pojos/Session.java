package br.upe.pojos;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

public class Session extends EventComponent {
    private Collection<Subscription> subscriptions;
    private Date startDate;
    private Date endDate;

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
    public Date getStartDate() {
        return this.startDate;
    }
    public Date getEndDate() {
        return this.endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}
