package me.akamex.luckapi.protocol.sidebar;

import com.comphenix.packetwrapper.WrapperPlayServerScoreboardDisplayObjective;
import com.comphenix.packetwrapper.WrapperPlayServerScoreboardObjective;
import com.comphenix.packetwrapper.WrapperPlayServerScoreboardScore;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import me.luckkyyz.luckapi.sidebar.SidebarRow;

import java.util.HashMap;
import java.util.Map;

class SidebarData {

    private String objectiveName, title;
    private Map<SidebarRow, RowData> rowDataMap = new HashMap<>();

    SidebarData(String objectiveName, String title) {
        this.objectiveName = objectiveName;
        this.title = title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<SidebarRow, RowData> getRowDataMap() {
        return rowDataMap;
    }

    void put(SidebarRow row, RowData rowData) {
        rowDataMap.put(row, rowData);
    }

    public WrapperPlayServerScoreboardObjective createObjective(int mode) {
        WrapperPlayServerScoreboardObjective objective = new WrapperPlayServerScoreboardObjective();
        objective.setName(objectiveName);
        objective.setMode(mode);
        objective.setDisplayName(WrappedChatComponent.fromText(title));
        return objective;
    }

    public WrapperPlayServerScoreboardDisplayObjective createDisplayObjective() {
        WrapperPlayServerScoreboardDisplayObjective objective = new WrapperPlayServerScoreboardDisplayObjective();
        objective.setPosition(1);
        objective.setScoreName(objectiveName);
        return objective;
    }

    public WrapperPlayServerScoreboardScore createScore(SidebarRow row, EnumWrappers.ScoreboardAction action) {
        RowData rowData = rowDataMap.get(row);
        if(rowData == null) {
            throw new IllegalArgumentException("Row incorrect!");
        }

        WrapperPlayServerScoreboardScore score = new WrapperPlayServerScoreboardScore();
        score.setScoreboardAction(action);
        score.setObjectiveName(objectiveName);
        score.setScoreName(rowData.getName());
        score.setValue(rowData.getValue());
        return score;
    }

    public static class RowData {

        private String name;
        private int value;

        RowData(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

}
