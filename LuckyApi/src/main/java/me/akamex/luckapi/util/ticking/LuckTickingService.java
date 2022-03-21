package me.akamex.luckapi.util.ticking;

import me.akamex.luckapi.util.scheduler.Scheduler;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class LuckTickingService implements TickingService {

    private Plugin plugin;
    private Map<Ticking, TickingObserver> tickingMap = new HashMap<>();
    private Scheduler tickingTask;

    public LuckTickingService(Plugin plugin) {
        this.plugin = plugin;
    }

    private class TickingTask extends Scheduler {

        private TickingTask(Plugin plugin) {
            super(plugin);
            runTaskTimer(1);
        }

        @Override
        public void run() {
            tickingMap.values().removeIf(TickingObserver::doTicking);

            if(tickingMap.isEmpty()) {
                cancel();
                tickingTask = null;
            }
        }
    }

    @Override
    public Ticking createTicking(Ticking ticking) {
        TickingObserver observer = ticking.getPeriod() == 1 ? new AlwaysTickingObserver(ticking) : new CountingTickingObserver(ticking);
        tickingMap.put(ticking, observer);
        if(tickingTask == null || tickingTask.isCancelled()) {
            tickingTask = new TickingTask(plugin);
        }
        return ticking;
    }

    @Override
    public Ticking removeTicking(Ticking ticking) {
        tickingMap.remove(ticking);

        if(tickingMap.isEmpty() && tickingTask != null) {
            tickingTask.cancel();
            tickingTask = null;
        }

        return ticking;
    }

    @Override
    public void cancel() {
        if(tickingTask != null) {
            tickingTask.cancel();
        }
        tickingMap.clear();
    }
}
