package me.akamex.luckapi.util.scheduler;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public abstract class Scheduler extends BukkitRunnable {

    protected final Plugin plugin;

    protected Scheduler(Plugin plugin) {
        this.plugin = plugin;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public BukkitTask runTask() {
        return runTask(plugin);
    }

    public BukkitTask runTaskAsynchronously() {
        return runTaskAsynchronously(plugin);
    }

    public BukkitTask runTaskLater(long delay) {
        return super.runTaskLater(plugin, delay);
    }

    public BukkitTask runTaskLaterAsynchronously(long delay) {
        return super.runTaskLaterAsynchronously(plugin, delay);
    }

    public BukkitTask runTaskTimer(long delay, long period) {
        return runTaskTimer(plugin, delay, period);
    }

    public BukkitTask runTaskTimerAsynchronously(long delay, long period) {
        return runTaskTimerAsynchronously(plugin, delay, period);
    }

    public BukkitTask runTaskTimer(long period) {
        return runTaskTimer(plugin, period, period);
    }

    public BukkitTask runTaskTimerAsynchronously(long period) {
        return runTaskTimerAsynchronously(plugin, period, period);
    }

    @Override
    public void cancel() {
        if(isCancelled()) {
            return;
        }
        super.cancel();
    }
}