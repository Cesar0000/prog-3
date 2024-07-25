package br.upe.pojos;

import br.upe.operations.EventCRUD;
import br.upe.operations.SubscriptionCRUD;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminUser extends User {
    private final boolean isAdmin = true;
    private Collection<GreatEvent> events;

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return this.email;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return this.password;
    }
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
    public UUID getUuid() {
        return this.uuid;
    }
    public void addSubscription(Subscription subscription){
        subscriptions.add(subscription);
    }

    public Collection<GreatEvent> getEvents(){
        return events;
    }
    public Collection<Subscription> getSubscriptions() {
        return subscriptions;
    }
    public void setSubscriptions(Collection<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public void setEvents(Collection<GreatEvent> events){this.events = events;}
    public boolean isAdmin() {
        return isAdmin;
    }

    public void addSubscriptions(Subscription subscription) {this.subscriptions.add(subscription);}
    public void addEvent(GreatEvent event) {this.events.add(event);}

}
