package br.upe.pojos;

import java.util.UUID;

public class Submission extends EventComponent {
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
    public UUID getUuid() {
        return this.uuid;
    }
    public UUID getEventUuid() {
        return eventUuid;
    }
    public void setEventUuid(UUID eventUuid) {
        this.eventUuid = eventUuid;
    }

}
