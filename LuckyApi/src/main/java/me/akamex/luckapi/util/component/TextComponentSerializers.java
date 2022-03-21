package me.akamex.luckapi.util.component;

import me.akamex.luckapi.config.ConfigurationUtil;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.List;

import static me.akamex.luckapi.util.color.ColorUtils.color;

public final class TextComponentSerializers {

    private TextComponentSerializers() {
        throw new UnsupportedOperationException();
    }

    public static YamlComponentSerializer yaml() {
        return YamlComponentSerializerImpl.INST;
    }

    private enum YamlComponentSerializerImpl implements YamlComponentSerializer {

        INST;

        @Override
        public ConfigurationSection serialize(YamlConfiguration config, String path, TextComponent component) {
            ConfigurationSection section = ConfigurationUtil.clearSection(config, path);

            if (component.getText() != null) {
                section.set("text", component.getText());
            }
            ClickEvent event = component.getClickEvent();
            if (event != null) {
                final ConfigurationSection click = section.createSection("click");
                click.set("action", event.getAction().name());
                click.set("value", event.getValue());
            }
            HoverEvent hoverEvent = component.getHoverEvent();
            if (hoverEvent != null) {
                ConfigurationSection hover = section.createSection("hover");
                hover.set("action", hoverEvent.getAction().name());
                hover.set("value", TextComponent.toLegacyText(hoverEvent.getValue()));
            }
            List<BaseComponent> components = component.getExtra();
            if (components != null && !components.isEmpty()) {
                ConfigurationSection extra = section.createSection("extra");
                for (int i = 0; i < components.size(); i++) {
                    BaseComponent got = components.get(i);
                    if (!(got instanceof TextComponent)) {
                        throw new IllegalArgumentException("The extra format does not available!");
                    }
                    extra.set(String.valueOf(i), serialize(config, extra.getCurrentPath() + "." + i, (TextComponent) got));
                }
            }

            return section;
        }

        @Override
        public TextComponent deserialize(Configuration config, String path) {
            ConfigurationSection section = ConfigurationUtil.checkSection(config, path);
            TextComponentBuilder component = TextComponentBuilder.newBuilder();

            component.setText(color(section.getString("text", "")));
            ConfigurationSection click = section.getConfigurationSection("click");
            if (click != null) {
                if (!click.isString("action") || !click.isString("value")) {
                    throw new IllegalArgumentException("The click format does not available!");
                }
                ClickEvent.Action action = ClickEvent.Action.valueOf(click.getString("action"));
                String value = color(click.getString("value", ""));
                component.onClick(action, value);
            }
            ConfigurationSection hover = section.getConfigurationSection("hover");
            if (hover != null) {
                if (!hover.isString("action") || !hover.isString("value")) {
                    throw new IllegalArgumentException("The click format does not available!");
                }
                HoverEvent.Action action = HoverEvent.Action.valueOf(hover.getString("action"));
                String value = color(hover.getString("value", ""));
                component.onHover(action, value);
            }
            ConfigurationSection extraSection = section.getConfigurationSection("extra");
            if (extraSection != null) {
                List<BaseComponent> extra = new ArrayList<>();
                extraSection.getKeys(false).forEach(got -> {
                    if (!extraSection.isConfigurationSection(got)) {
                        throw new IllegalArgumentException("The extra format does not available!");
                    }
                    extra.add(deserialize(config, got));
                });
                component.setExtra(extra);
            }
            return component.create();
        }
    }

}
