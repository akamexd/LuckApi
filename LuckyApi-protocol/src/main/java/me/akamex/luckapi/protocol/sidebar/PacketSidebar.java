package me.akamex.luckapi.protocol.sidebar;

import com.comphenix.packetwrapper.WrapperPlayServerScoreboardObjective;
import com.comphenix.protocol.wrappers.EnumWrappers;
import me.luckkyyz.luckapi.sidebar.AbstractSidebar;
import me.luckkyyz.luckapi.sidebar.Sidebar;
import me.luckkyyz.luckapi.sidebar.SidebarRow;
import me.luckkyyz.luckapi.util.color.ColorUtils;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class PacketSidebar extends AbstractSidebar {

    private Function<Player, String> titleGenerator;
    private Map<UUID, SidebarData> sidebarDataMap = new ConcurrentHashMap<>();

    private PacketSidebar(String name, Function<Player, String> titleGenerator, List<SidebarRow> rows) {
        super(name, rows);
        this.titleGenerator = titleGenerator;

        rows.forEach(row -> row.addUpdateCallback((player, text) -> {
            if(!sidebarDataMap.containsKey(player.getUniqueId())) {
                return;
            }
            SidebarData sidebarData = sidebarDataMap.get(player.getUniqueId());
            SidebarData.RowData rowData = sidebarData.getRowDataMap().get(row);
            sidebarData.createScore(row, EnumWrappers.ScoreboardAction.REMOVE).sendPacket(player);

            rowData.setName(text);
            sidebarData.createScore(row, EnumWrappers.ScoreboardAction.CHANGE).sendPacket(player);
        }));
    }

    private String getTitle(Player player) {
        return ColorUtils.color(titleGenerator.apply(player));
    }

    @Override
    public void update(Player player) {
        if(!sidebarDataMap.containsKey(player.getUniqueId())) {
            return;
        }
        SidebarData sidebarData = sidebarDataMap.get(player.getUniqueId());
        sidebarData.setTitle(getTitle(player));
        sidebarData.createObjective(WrapperPlayServerScoreboardObjective.Mode.UPDATE_VALUE).sendPacket(player);

        super.update(player);
    }

    @Override
    public void show(Player player) {
        if(sidebarDataMap.containsKey(player.getUniqueId())) {
            return;
        }
        SidebarData sidebarData = new SidebarData(name, getTitle(player));
        sidebarData.createObjective(WrapperPlayServerScoreboardObjective.Mode.ADD_OBJECTIVE).sendPacket(player);
        sidebarData.createDisplayObjective().sendPacket(player);

        for (int index = 0, score = (rows.size() - 1); index < rows.size(); index++, score--) {
            SidebarRow row = rows.get(index);
            SidebarData.RowData rowData = new SidebarData.RowData(row.getText(player), score);
            sidebarData.put(row, rowData);

            sidebarData.createScore(row, EnumWrappers.ScoreboardAction.CHANGE).sendPacket(player);
        }

        sidebarDataMap.put(player.getUniqueId(), sidebarData);
    }

    @Override
    public void hide(Player player) {
        if(!sidebarDataMap.containsKey(player.getUniqueId())) {
            return;
        }
        SidebarData sidebarData = sidebarDataMap.get(player.getUniqueId());

        sidebarData.createObjective(WrapperPlayServerScoreboardObjective.Mode.REMOVE_OBJECTIVE).sendPacket(player);
        sidebarData.getRowDataMap().keySet().forEach(row -> sidebarData.createScore(row, EnumWrappers.ScoreboardAction.REMOVE).sendPacket(player));

        sidebarDataMap.remove(player.getUniqueId());
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder extends Sidebar.Builder {

        private Builder() {
        }

        @Override
        public Sidebar create() {
            return new PacketSidebar(name, titleGenerator, rows);
        }
    }
}
