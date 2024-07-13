package br.upe.pojos;

import java.util.UUID;

public class Subscription {

    private UUID uuid;
    private UUID sessionUuid;
    private UUID userUuid;

    public UUID getUuid() {
        return this.uuid;
    }
    public UUID getSessionUuid() {
        return sessionUuid;
    }
    public UUID getUserUuid() {
        return userUuid;
    }
    public void setUserUuid(UUID userUuid) {
        this.userUuid = userUuid;
    }
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
    public void setSessionUuid(UUID sessionUuid) {
        this.sessionUuid = sessionUuid;
    }
}
