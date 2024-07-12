package br.upe.pojos;

public class Subscription {
    private String validationCode;
    private String sessionHexCode;

    public void setSessionHexCode(String sessionHexCode) {
        this.sessionHexCode = sessionHexCode;
    }
    public String getSessionHexCode() {
        return sessionHexCode;
    }
    public void setValidationCode(String validationCode){
        this.validationCode = validationCode;
    }
    public String getValidationCode(){
        return this.validationCode;
    }
}
