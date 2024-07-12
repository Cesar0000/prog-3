package br.upe.pojos;

import java.util.Collection;

public class CommomUser extends User {

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

    public void setSubscriptions(Subscription subscription) {
        this.subscriptions.add(subscription);
    }
    public Collection<Subscription> getSubscriptions() {
        return this.subscriptions;
    }

}
