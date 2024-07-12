package br.upe.pojos;

import java.util.Collection;

public abstract class User {
    protected String name;
    protected String password;

    private Collection<Subscription> subscriptions;

    public abstract void setName(String name);
    public abstract String getName();
    public abstract void setPassword(String password);
    public abstract String getPassword();
}
