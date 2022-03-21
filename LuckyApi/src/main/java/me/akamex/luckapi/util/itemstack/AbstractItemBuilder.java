package me.akamex.luckapi.util.itemstack;

import me.akamex.luckapi.util.color.ColorUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static me.akamex.luckapi.util.color.ColorUtils.color;

@SuppressWarnings("unchecked")
public abstract class AbstractItemBuilder<B extends AbstractItemBuilder<B, M>, M extends ItemMeta> implements ItemBuilder<B> {

    protected ItemStack itemStack;
    protected M meta;

    protected AbstractItemBuilder(ItemStack pattern) {
        this.itemStack = pattern.clone();
        this.meta = (M) itemStack.getItemMeta();
    }

    @Override
    public ItemStack create() {
        if(meta != null) {
            itemStack.setItemMeta(meta);
        }
        return itemStack;
    }

    @Override
    public B setType(Material type) {
        itemStack.setType(type);
        return (B) this;
    }

    @Override
    public B setAmount(int amount) {
        itemStack.setAmount(amount);
        return (B) this;
    }

    @Override
    @Deprecated
    public B setDurability(short durability) {
        itemStack.setDurability(durability);
        return (B) this;
    }

    @Override
    public B addEnchantments(Map<Enchantment, Integer> enchantments) {
        itemStack.addEnchantments(enchantments);
        return (B) this;
    }

    @Override
    public B addEnchantment(Enchantment enchantment, int level) {
        itemStack.addEnchantment(enchantment, level);
        return (B) this;
    }

    @Override
    public B setDisplay(String display) {
        meta.setDisplayName(color(display));
        return (B) this;
    }

    @Override
    public B setLocalized(String localized) {
        meta.setLocalizedName(color(localized));
        return (B) this;
    }

    @Override
    public B setLore(Collection<String> lore) {
        meta.setLore(ColorUtils.color(lore));
        return (B) this;
    }

    @Override
    public B addLoreLine(String loreLine) {
        List<String> lore = meta.getLore();
        if(lore == null) {
            lore = new ArrayList<>();
        }
        lore.add(color(loreLine));
        meta.setLore(lore);
        return (B) this;
    }

    @Override
    public B addFlags(ItemFlag... flags) {
        meta.addItemFlags(flags);
        return (B) this;
    }

    @Override
    public B setUnbreakable(boolean unbreakable) {
        meta.setUnbreakable(unbreakable);
        return (B) this;
    }
}
