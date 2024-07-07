package br.upe.pojos;

public abstract class PlainEvent {
    protected String name;
    protected String director;
    protected String startDate;
    protected String endDate;

    abstract void setName(String name);
    abstract void setDirector(String director);
    abstract void setStartDate(String startDate);
    abstract void setEndDate(String endDate);
    
    abstract String getName();
    abstract String getDirector();
    abstract String getStartDate();
    abstract String getEndDate();
}
