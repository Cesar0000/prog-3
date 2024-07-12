package br.upe.pojos;

import javax.management.relation.Role;
import java.util.Collection;

public abstract class User {
    protected String name;
    protected String password;
    protected String email;


    protected Collection<Subscription> subscriptions;

    public abstract void setEmail(String email);
    public abstract String getEmail();

    public abstract void setName(String name);
    public abstract String getName();

    public abstract void setPassword(String password);
    public abstract String getPassword();

    public abstract void setSubscriptions(Subscription subscription);
}
