package br.upe.pojos;
import java.util.Collection;
import java.util.UUID;
import java.time.LocalDateTime;

public class GreatEvent {

    private String descritor;
    private String director;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private UUID uuid;
    private Collection<Session> sessions;
    private Collection<Submission> submissions;

    public Collection<Submission> getSubmissions() {
        return submissions;
    }
    public void setSubmissions(Collection<Submission> submissions) {
        this.submissions = submissions;
    }
    public void addSubmission(Submission submission) {
        this.submissions.add(submission);
    }
    public void setDescritor(String descritor) {
        this.descritor = descritor;
    }
    public void setDirector(String director) {
        this.director = director;
    }
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }
    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }
    public String getDescritor() {
        return this.descritor;
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
