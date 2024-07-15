package br.upe.pojos;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

public class GreatEvent {

    private String descritor;
    private String director;
    private Date startDate;
    private Date endDate;
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
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public String getDescritor() {
        return this.descritor;
    }
    public String getDirector() {
        return this.director;
    }
    public Date getStartDate() {
        return this.startDate;
    }
    public Date getEndDate() {
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
