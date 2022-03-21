package me.akamex.luckapi.placeholder;

import me.akamex.luckapi.api.Creatable;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemPlaceholder implements Creatable<ItemStack> {

    private ItemStack itemStack;

    private ItemPlaceholder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public static ItemPlaceholder newPlaceholder(ItemStack itemStack) {
        return new ItemPlaceholder(itemStack);
    }

    public static ItemStack placeholder(ItemStack itemStack, String holder, Object to) {
        ItemMeta meta = itemStack.getItemMeta();
        if(meta == null) {
            meta = Bukkit.getItemFactory().getItemMeta(itemStack.getType());
        }

        if(meta == null) {
            throw new NullPointerException();
        }

        if(meta.hasDisplayName()) {
            String display = meta.getDisplayName();
            display = display.replaceAll(holder, to.toString());
            meta.setDisplayName(display);
        }

        if(meta.hasLocalizedName()) {
            String localized = meta.getLocalizedName();
            localized = localized.replaceAll(holder, to.toString());
            meta.setLocalizedName(localized);
        }

        if(meta.hasLore()) {
            List<String> lore = meta.getLore();
            if(lore == null) {
                lore = new ArrayList<>();
            }
            lore.replaceAll(line -> line.replaceAll(holder, to.toString()));
            meta.setLore(lore);
        }

        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public ItemPlaceholder placeholder(String holder, Object to) {
        placeholder(itemStack, holder, to);
        return this;
    }

    public <T> ItemPlaceholder placeholder(Placeholder<T> placeholder, T value) {
        placeholder(itemStack, placeholder.getHolder(), placeholder.getApplier().apply(value));
        return this;
    }

    public <T> ItemPlaceholder placeholder(ValuedPlaceholder<T> placeholder) {
        placeholder(itemStack, placeholder.getHolder(), placeholder.getValue());
        return this;
    }

    @Override
    public ItemStack create() {
        return itemStack;
    }

}
