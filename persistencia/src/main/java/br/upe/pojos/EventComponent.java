package br.upe.pojos;

import java.util.UUID;

public abstract class EventComponent {
    protected UUID uuid;
    protected UUID eventUuid;
    protected String descritor;

    public abstract UUID getUuid();
    public abstract void setUuid(UUID uuid);
    public abstract UUID getEventUuid();
    public abstract void setEventUuid(UUID eventUuid);
    public abstract void setDescritor(String descritor);
    public abstract String getDescritor();

}
