package me.akamex.luckapi.util.itemstack;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilderImpl extends AbstractItemBuilder<ItemBuilderImpl, ItemMeta> {

    ItemBuilderImpl(ItemStack pattern) {
        super(pattern);
    }

}
