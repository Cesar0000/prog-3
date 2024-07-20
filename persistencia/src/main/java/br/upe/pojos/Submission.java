package br.upe.pojos;

import java.util.Date;
import java.util.UUID;

public class Submission extends EventComponent {
    private UUID userUuid;
    private Date date;

    public void setDescritor(String descritor) {
        this.descritor = descritor;
    }
    public String getDescritor(){
        return this.descritor;
    }
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
    public UUID getUuid() {
        return this.uuid;
    }
    public UUID getEventUuid() {
        return eventUuid;
    }
    public void setEventUuid(UUID eventUuid) {
        this.eventUuid = eventUuid;
    }
    public void setUserUuid(UUID userUuid){
        this.userUuid = userUuid;
    }
    public UUID getUserUuid(){
        return userUuid;
    }
    public void setDate(Date date){
        this.date = date;
    }
    public Date getDate(){
        return this.date;
    }
}
