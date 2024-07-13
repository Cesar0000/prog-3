package br.upe.pojos;

import java.util.UUID;

public abstract class EventComponent {
    protected UUID uuid;
    protected UUID eventUuid;

    public abstract UUID getUuid();
    public abstract void setUuid(UUID uuid);
    public abstract UUID getEventUuid();
    public abstract void setEventUuid(UUID eventUuid);

}
