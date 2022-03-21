package me.akamex.luckapi.cooldown;

import me.akamex.luckapi.LuckServiceApi;
import me.akamex.luckapi.util.function.Optionality;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface CooldownService extends LuckServiceApi {

    Cooldown createCooldown(Cooldown cooldown);

    default Cooldown createCooldown(Cooldown.Builder builder) {
        return createCooldown(builder.create());
    }

    Optionality<Cooldown> getCooldown(UUID uuid);

    default Optionality<Cooldown> getCooldown(Player player) {
        return getCooldown(player.getUniqueId());
    }

    default boolean hasCooldown(UUID uuid) {
        return getCooldown(uuid).isPresent();
    }

    default boolean hasCooldown(Player player) {
        return hasCooldown(player.getUniqueId());
    }

    default boolean checkCooldown(UUID uuid) {
        Cooldown cooldown = getCooldown(uuid).orElse(null);
        if(cooldown != null) {
            cooldown.getCheckingAction().run();
            return true;
        }
        return false;
    }

    default boolean checkCooldown(Player player) {
        return checkCooldown(player.getUniqueId());
    }

    Optionality<Cooldown> removeCooldown(UUID uuid);

    default Optionality<Cooldown> removeCooldown(Player player) {
        return removeCooldown(player.getUniqueId());
    }

}
