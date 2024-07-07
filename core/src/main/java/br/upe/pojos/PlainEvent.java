package br.upe.pojos;

public abstract class PlainEvent {
    String name;
    String director;
    String startDate;
    String endDate;

    abstract void setName(String name);
    abstract void setDirector(String director);
    abstract void setStartDate(String startDate);
    abstract void setEndDate(String endDate);
    
    abstract String getName();
    abstract String getDirector();
    abstract String getStartDate();
    abstract String getEndDate();
}
