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
    public Collection<String> getSessionsHexCode(){
        return this.sessionsHexCode;
    }
    public void setSessionsHexCode(Collection<String> sessionsHexCode){
        this.sessionsHexCode = sessionsHexCode;
    }
    public void addSessionHexCode(String sessionHexCode){
        this.sessionsHexCode.add(sessionHexCode);
    }
}
