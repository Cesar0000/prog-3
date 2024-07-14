package br.upe.pojos;
import java.util.Collection;
import java.util.UUID;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GreatEvent {

    private String name;
    private String director;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private UUID uuid;
    private Collection<Session> sessions;

    public void setName(String name) {
        this.name = name;
    }
    public void setDirector(String director) {
        this.director = director;
    }
    public void setStartDateTime(LocalDateTime StartDateTime) {this.startDateTime = startDateTime;
    }
    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }
    public String getName() {
        return this.name;
    }
    public String getDirector() {
        return this.director;
    }
    public LocalDateTime getStartDateTime() {
        return this.startDateTime;
    }
    public LocalDateTime getEndDateTime() {
        return this.endDateTime;
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
