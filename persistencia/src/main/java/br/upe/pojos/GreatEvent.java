package br.upe.pojos;
import java.util.Collection;
import java.util.UUID;

public class GreatEvent {

    private String name;
    private String director;
    private String startDate;
    private String endDate;
    private UUID uuid;
    private Collection<Session> sessions;

    public void setName(String name) {
        this.name = name;
    }
    public void setDirector(String director) {
        this.director = director;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public String getName() {
        return this.name;
    }
    public String getDirector() {
        return this.director;
    }
    public String getStartDate() {
        return this.startDate;
    }
    public String getEndDate() {
        return this.endDate;
    }
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
    public UUID getUuid() {
        return this.uuid;
    }
    public Collection<Session> getSessions(){
        return this.sessions;
    }
    public void setSessions(Collection<Session> sessions){
        this.sessions = sessions;
    }
    public void addSession(Session session){
        this.sessions.add(session);
    }
}
