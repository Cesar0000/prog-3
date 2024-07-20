package br.upe.pojos;

import java.util.Collection;
import java.util.UUID;

public abstract class User {
    protected boolean isAdmin;
    protected String name;
    protected String password;
    protected String email;
    protected UUID uuid;
    protected Collection<Subscription> subscriptions;

    public abstract void setEmail(String email);
    public abstract String getEmail();

    public abstract void setName(String name);
    public abstract String getName();

    public abstract void setPassword(String password);
    public abstract String getPassword();

    public abstract void setUuid(UUID uuid);
    public abstract UUID getUuid();

    public abstract void addSubscription(Subscription subscription);
    public abstract Collection<Subscription> getSubscriptions();
    public abstract void setSubscriptions(Collection<Subscription> subscriptions);

    public abstract boolean isAdmin();
}
