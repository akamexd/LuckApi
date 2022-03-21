package me.akamex.luckapi.util.itemstack.reader;

import me.akamex.luckapi.util.color.ColorUtils;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ItemReaders {

    private ItemReaders() {
        throw new UnsupportedOperationException();
    }

    public static YamlItemReader yaml() {
        return YamlItemReaderImpl.INST;
    }

    private enum YamlItemReaderImpl implements YamlItemReader {

        INST;

        @Override
        @SuppressWarnings("deprecation")
        public ReadItem read(ConfigurationSection section) {
            if(section == null) {
                return null;
            }
            Material type = Material.getMaterial(section.getString("type", ""));
            int amount = section.getInt("amount", 1);
            Map<Enchantment, Integer> enchantments = new HashMap<>();

            ConfigurationSection enchantmentSection = section.getConfigurationSection("enchantments");
            if(enchantmentSection != null) {
                enchantmentSection.getKeys(false).forEach(key -> enchantments.put(Enchantment.getByName(key), enchantmentSection.getInt(key, 0)));
            }

            String display = section.getString("display");
            if(display != null) {
                display = ColorUtils.color(display);
            }

            int customModelData = section.getInt("customModelData", -1);
            List<String> lore = ColorUtils.color(section.getStringList("lore"));

            List<ItemFlag> flags = new ArrayList<>();
            section.getStringList("flags").forEach(key -> flags.add(ItemFlag.valueOf(key)));

            return new ReadItem(type, amount, enchantments, display, lore, flags.toArray(new ItemFlag[0]), customModelData);
        }
    }

}
