package me.akamex.luckapi.menu;

import me.akamex.luckapi.menu.button.MenuButton;
import me.akamex.luckapi.menu.filling.FillingStrategy;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.HashMap;
import java.util.Map;

public class MenuSession implements InventoryHolder {

    private PreparedMenu<?> menu;
    private Player player;
    private Inventory inventory;
    private Map<Integer, MenuButton> buttonMap = new HashMap<>();

    private int page = 0;

    public MenuSession(Player player) {
        this.player = player;
    }

    public int getPage() {
        return page;
    }

    public boolean nextPage() {
        page++;
        update();
        return true;
    }

    public boolean previousPage() {
        if(page == 0) {
            return false;
        }
        page--;
        update();
        return true;
    }

    public Player getPlayer() {
        return player;
    }

    public PreparedMenu<?> getMenu() {
        return menu;
    }

    public Map<Integer, MenuButton> getButtonMap() {
        return buttonMap;
    }

    public void setMenu(PreparedMenu<?> menu) {
        this.menu = menu;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public void update() {
        inventory.clear();
        FillingStrategy fillingStrategy = menu.getFillingStrategy();
        FillingStrategy.ResultBuilder resultBuilder = FillingStrategy.ResultBuilder.newBuilder();
        fillingStrategy.generate(resultBuilder, this);
        buttonMap = resultBuilder.create();
        buttonMap.forEach((slot, button) -> inventory.setItem(slot, button.getItemStack()));
        player.updateInventory();
    }

    public void setButton(int slot, MenuButton button) {
        buttonMap.put(slot, button);
        inventory.setItem(slot, button.getItemStack());
    }

}
