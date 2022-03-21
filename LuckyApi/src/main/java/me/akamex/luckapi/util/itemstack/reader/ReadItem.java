package me.akamex.luckapi.util.itemstack.reader;

import me.akamex.luckapi.util.MinecraftVersion;
import me.akamex.luckapi.util.function.Optionality;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class ReadItem implements UnaryOperator<ItemStack>, Supplier<ItemStack> {

    private Material type;
    private int amount;
    private Map<Enchantment, Integer> enchantments;
    private String display;
    private List<String> lore;
    private ItemFlag[] flags;
    private int customModelData;

    private ItemStack built;

    ReadItem(Material type, int amount, Map<Enchantment, Integer> enchantments, String display, List<String> lore, ItemFlag[] flags, int customModelData) {
        this.type = type;
        this.amount = amount;
        this.enchantments = enchantments == null ? new HashMap<>() : enchantments;
        this.display = display;
        this.lore = lore == null ? new ArrayList<>() : lore;
        this.flags = flags == null ? new ItemFlag[0] : flags;
        this.customModelData = customModelData;
    }

    public Optionality<Material> getType() {
        return Optionality.optionalOfNullable(type);
    }

    public int getAmount() {
        return amount;
    }

    public Map<Enchantment, Integer> getEnchantments() {
        return enchantments;
    }

    public Optionality<String> getDisplay() {
        return Optionality.optionalOfNullable(display);
    }

    public List<String> getLore() {
        return lore;
    }

    public ItemFlag[] getFlags() {
        return flags;
    }

    @Override
    public ItemStack apply(ItemStack stack) {
        ItemMeta meta = stack.getItemMeta();
        if(meta == null) {
            return stack;
        }

        if(display != null) {
            meta.setDisplayName(display);
        }
        meta.setLore(lore);
        meta.addItemFlags(flags);
        if(customModelData != -1 && MinecraftVersion.getCurrent().newerOrEqual(MinecraftVersion.v1_16_R1)) {
            meta.setCustomModelData(customModelData);
        }

        enchantments.forEach((enchantment, level) -> meta.addEnchant(enchantment, level, false));
        stack.setItemMeta(meta);
        return stack;
    }

    @Override
    public ItemStack get() {
        if(built == null) {
            if(type == null) {
                throw new NullPointerException("Type null!");
            }
            built = apply(new ItemStack(type, amount));
        }
        return built.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReadItem readItem = (ReadItem) o;
        return new EqualsBuilder()
                .append(amount, readItem.amount)
                .append(type, readItem.type)
                .append(enchantments, readItem.enchantments)
                .append(display, readItem.display)
                .append(lore, readItem.lore)
                .append(flags, readItem.flags)
                .append(built, readItem.built)
                .append(customModelData, readItem.customModelData)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(type)
                .append(amount)
                .append(enchantments)
                .append(display)
                .append(lore)
                .append(flags)
                .append(built)
                .append(customModelData)
                .toHashCode();
    }
}
