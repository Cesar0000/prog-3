package br.upe.pojos;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Subscription {

    private UUID uuid;
    private UUID sessionUuid;
    private UUID userUuid;
    private Date date;

    public UUID getUuid() {
        return this.uuid;
    }
    public UUID getSessionUuid() {
        return sessionUuid;
    }
    public UUID getUserUuid() {
        return userUuid;
    }
    public void setUserUuid(UUID userUuid) {
        this.userUuid = userUuid;
    }
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
    public void setSessionUuid(UUID sessionUuid) {
        this.sessionUuid = sessionUuid;
    }
    public void setDate(Date date){
        this.date = date;
    }
    public Date getDate(){
        return this.date;
    }

}
