package me.akamex.luckapi.menu.reader;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.event.inventory.InventoryType;

import java.util.Map;

public class ReadMenu {

    private String title;
    private int size;
    private InventoryType inventoryType;
    private Map<Integer, ReadButton> buttonMap;

    ReadMenu(String title, int size, InventoryType inventoryType, Map<Integer, ReadButton> buttonMap) {
        this.title = title;
        this.size = size;
        this.inventoryType = inventoryType;
        this.buttonMap = buttonMap;
    }

    public String getTitle() {
        return title;
    }

    public int getSize() {
        return size;
    }

    public InventoryType getInventoryType() {
        return inventoryType;
    }

    public Map<Integer, ReadButton> getButtonMap() {
        return buttonMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReadMenu readMenu = (ReadMenu) o;
        return new EqualsBuilder()
                .append(size, readMenu.size)
                .append(title, readMenu.title)
                .append(inventoryType, readMenu.inventoryType)
                .append(buttonMap, readMenu.buttonMap)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(title)
                .append(size)
                .append(inventoryType)
                .append(buttonMap)
                .toHashCode();
    }
}
