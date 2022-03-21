package me.akamex.luckapi.cooldown;

import me.akamex.luckapi.api.Creatable;
import me.akamex.luckapi.util.player.PlayerData;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.function.Consumer;

public class Cooldown {

    private UUID uuid;
    private long time;
    private Runnable expireAction, forceRemoveAction, checkingAction;
    private boolean saveAfterReconnect;

    public Cooldown(UUID uuid, long time, Consumer<PlayerData> expireAction, Consumer<PlayerData> forceRemoveAction, Consumer<PlayerData> checkingAction, boolean saveAfterReconnect) {
        this.uuid = uuid;
        this.time = time;
        PlayerData playerData = new PlayerData(uuid);
        this.expireAction = () -> expireAction.accept(playerData);
        this.forceRemoveAction = () -> forceRemoveAction.accept(playerData);
        this.checkingAction = () -> checkingAction.accept(playerData);
        this.saveAfterReconnect = saveAfterReconnect;
    }

    boolean checkTime() {
        if(time > 0) {
            time--;
            return true;
        }
        return false;
    }

    public UUID getUuid() {
        return uuid;
    }

    public long getTime() {
        return time;
    }

    public Runnable getExpireAction() {
        return expireAction;
    }

    public Runnable getForceRemoveAction() {
        return forceRemoveAction;
    }

    public Runnable getCheckingAction() {
        return checkingAction;
    }

    public boolean isSaveAfterReconnect() {
        return saveAfterReconnect;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cooldown cooldown = (Cooldown) o;
        return new EqualsBuilder().append(time, cooldown.time)
                .append(saveAfterReconnect, cooldown.saveAfterReconnect)
                .append(uuid, cooldown.uuid)
                .append(expireAction, cooldown.expireAction)
                .append(forceRemoveAction, cooldown.forceRemoveAction)
                .append(checkingAction, cooldown.checkingAction)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(uuid)
                .append(time)
                .append(expireAction)
                .append(forceRemoveAction)
                .append(checkingAction)
                .append(saveAfterReconnect)
                .toHashCode();
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder implements Creatable<Cooldown> {

        private UUID uuid = UUID.randomUUID();
        private long time = 10;
        private Consumer<PlayerData> expireAction = pl -> {}, forceRemoveAction = pl -> {}, checkingAction = pl -> {};
        private boolean saveAfterReconnect = true;

        private Builder() {
        }

        public Builder setUuid(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        public Builder setPlayer(Player player) {
            return setUuid(player.getUniqueId());
        }

        public Builder setTime(long time) {
            this.time = time;
            return this;
        }

        public Builder setExpireAction(Consumer<PlayerData> expireAction) {
            this.expireAction = expireAction;
            return this;
        }

        public Builder setForceRemoveAction(Consumer<PlayerData> forceRemoveAction) {
            this.forceRemoveAction = forceRemoveAction;
            return this;
        }

        public Builder setCheckingAction(Consumer<PlayerData> checkingAction) {
            this.checkingAction = checkingAction;
            return this;
        }

        public Builder setSaveAfterReconnect(boolean saveAfterReconnect) {
            this.saveAfterReconnect = saveAfterReconnect;
            return this;
        }

        @Override
        public Cooldown create() {
            return new Cooldown(uuid, time, expireAction, forceRemoveAction, checkingAction, saveAfterReconnect);
        }
    }
}
