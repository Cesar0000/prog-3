package br.upe.pojos;

public abstract class User {
    protected String name;
    protected String password;

    public abstract void setName(String name);
    public abstract String getName();
    public abstract void setPassword(String password);
    public abstract String getPassword();
}
