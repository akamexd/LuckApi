package me.akamex.luckapi.cooldown;

import me.akamex.luckapi.util.function.Optionality;
import me.akamex.luckapi.util.scheduler.Scheduler;
import me.akamex.luckapi.util.scheduler.SchedulerTicks;
import me.akamex.luckapi.event.QuickEventListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LuckCooldownService implements CooldownService {

    private Plugin plugin;
    private Map<UUID, Cooldown> cooldownMap = new HashMap<>();
    private Scheduler cooldownTask;

    public LuckCooldownService(Plugin plugin) {
        this.plugin = plugin;

        QuickEventListener.newListener().event(PlayerQuitEvent.class, event -> {
            removeQuit(event.getPlayer());
        }).event(PlayerKickEvent.class, event -> {
            removeQuit(event.getPlayer());
        }).register(plugin);
    }

    private void removeQuit(Player player) {
        getCooldown(player).ifPresent(cooldown -> {
            if(cooldown.isSaveAfterReconnect()) {
                return;
            }
            cooldownMap.remove(cooldown.getUuid());
        });
    }

    private class CooldownTask extends Scheduler {

        private CooldownTask(Plugin plugin) {
            super(plugin);
            runTaskTimerAsynchronously(SchedulerTicks.SECOND);
        }

        @Override
        public void run() {
            cooldownMap.values().removeIf(cooldown -> {
                if(!cooldown.checkTime()) {
                    Bukkit.getScheduler().runTask(plugin, () -> cooldown.getExpireAction().run());
                    return true;
                }
                return false;
            });
        }
    }

    @Override
    public Cooldown createCooldown(Cooldown cooldown) {
        cooldownMap.put(cooldown.getUuid(), cooldown);

        if(cooldownTask == null) {
            cooldownTask = new CooldownTask(plugin);
        }

        return cooldown;
    }

    @Override
    public Optionality<Cooldown> getCooldown(UUID uuid) {
        return Optionality.optionalOfNullable(cooldownMap.get(uuid));
    }

    @Override
    public Optionality<Cooldown> removeCooldown(UUID uuid) {
        return getCooldown(uuid).ifPresent(cooldown -> {
            cooldownMap.remove(cooldown.getUuid());
            cooldown.getCheckingAction().run();

            if(cooldownMap.isEmpty()) {
                cooldownTask.cancel();
                cooldownTask = null;
            }
        });
    }

    @Override
    public void cancel() {
        if(cooldownTask != null) {
            cooldownTask.cancel();
        }
        cooldownMap.clear();
    }
}
