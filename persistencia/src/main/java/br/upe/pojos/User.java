package br.upe.pojos;

import java.util.Collection;

public abstract class User {
    protected String name;
    protected String password;
    protected String email;

    protected Collection<String> sessionsHexCode;

    public abstract void setEmail(String email);
    public abstract String getEmail();

    public abstract void setName(String name);
    public abstract String getName();

    public abstract void setPassword(String password);
    public abstract String getPassword();

    public abstract void setSessionsHexCode(Collection<String> sessionsHexCode);
    public abstract Collection<String> getSessionsHexCode();
    public abstract void addSessionHexCode(String sessionHexCode);
}
