package me.akamex.luckapi.sidebar;

import me.akamex.luckapi.util.color.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

class SidebarImpl extends AbstractSidebar {

    private Function<Player, String> titleGenerator;
    private Map<UUID, Scoreboard> scoreboardMap = new ConcurrentHashMap<>();

    SidebarImpl(String name, Function<Player, String> titleGenerator, List<SidebarRow> rows) {
        super(name, rows);
        this.titleGenerator = titleGenerator;

        rows.forEach(row -> row.addUpdateCallback((player, text) -> {
            Scoreboard scoreboard = scoreboardMap.get(player.getUniqueId());
            Team team = scoreboard.getTeam(String.format("%s.%s", name, row.getId()));
            if(team == null) {
                throw new UnsupportedOperationException();
            }
            team.setPrefix(text);
        }));
    }

    private String getTitle(Player player) {
        return ColorUtils.color(titleGenerator.apply(player));
    }

    @Override
    public void update(Player player) {
        Scoreboard scoreboard = scoreboardMap.get(player.getUniqueId());
        Objective objective = scoreboard.getObjective(name);
        if(objective != null) {
            objective.setDisplayName(getTitle(player));
        }

        super.update(player);
    }

    private Scoreboard getScoreboard(Player player) {
        if (scoreboardMap.containsKey(player.getUniqueId())) {
            return scoreboardMap.get(player.getUniqueId());
        }

        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        if(scoreboardManager == null) {
            throw new UnsupportedOperationException();
        }
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective(name, "dummy", getTitle(player));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        scoreboardMap.put(player.getUniqueId(), scoreboard);

        for (int index = 0, score = (rows.size() - 1); index < rows.size(); index++, score--) {
            SidebarRow row = rows.get(index);
            Team team = scoreboard.registerNewTeam(String.format("%s.%s", name, row.getId()));
            ChatColor chatColor = ChatColor.values()[index];
            team.addEntry(chatColor.toString());
            objective.getScore(chatColor.toString()).setScore(score);
            row.update(player);
        }
        return scoreboard;
    }

    @Override
    public void show(Player player) {
        player.setScoreboard(getScoreboard(player));
    }

    @Override
    public void hide(Player player) {
        scoreboardMap.remove(player.getUniqueId());
        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        if(scoreboardManager == null) {
            throw new UnsupportedOperationException();
        }
        Scoreboard scoreboard = scoreboardManager.getMainScoreboard();
        player.setScoreboard(scoreboard);
    }
}
