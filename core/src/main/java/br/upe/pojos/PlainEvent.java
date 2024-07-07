package br.upe.pojos;

public abstract class PlainEvent {
    protected String name;
    protected String director;
    protected String startDate;
    protected String endDate;

    public abstract void setName(String name);
    public abstract void setStartDate(String startDate);
    public abstract void setDirector(String director);
    public abstract void setEndDate(String endDate);
    
    public abstract String getName();
    public abstract String getDirector();
    public abstract String getStartDate();
    public abstract String getEndDate();
}
