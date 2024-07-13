package br.upe.pojos;
import java.util.Collection;

public class GreatEvent {

    private String name;
    private String director;
    private String startDate;
    private String endDate;


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
}
