package br.upe.pojos;

public class Subscription {
    private String validationCode;

    public void setValidationCode(User user){
        int hash = 7;
        hash = 31 * hash + (user.name == null ? 0 : user.name.hashCode());
        hash = 31 * hash + (user.email == null ? 0 : user.email.hashCode());
        this.validationCode = String.valueOf(hash);
    }

    public String getValidationCode(){
        return this.validationCode;
    }
}
