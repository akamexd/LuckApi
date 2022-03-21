package me.akamex.luckapi.message.serialize;

import me.akamex.luckapi.util.color.ColorUtils;
import me.akamex.luckapi.util.component.TextComponentSerializers;
import me.akamex.luckapi.util.component.YamlComponentSerializer;
import me.akamex.luckapi.message.ComponentMessage;
import me.akamex.luckapi.message.Message;
import me.akamex.luckapi.message.StringMessage;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public final class MessageSerializers {

    private MessageSerializers() {
        throw new UnsupportedOperationException();
    }

    public static YamlMessageSerializer yaml() {
        return YamlMessageSerializerImpl.INST;
    }

    private enum YamlMessageSerializerImpl implements YamlMessageSerializer {

        INST;

        private final YamlComponentSerializer componentSerializer = TextComponentSerializers.yaml();

        @Override
        public ConfigurationSection serialize(YamlConfiguration config, String path, Message message) {

            return null;
        }

        @Override
        public Message deserialize(Configuration config, String path) {
            if(config.isConfigurationSection(path)) {
                return new ComponentMessage(componentSerializer.deserialize(config, path));
            }
            if(config.isString(path)) {
                return new StringMessage(ColorUtils.color(config.getString(path, "")));
            }
            if(config.isList(path)) {
                return new StringMessage(ColorUtils.color(String.join("\n", config.getStringList(path))));
            }

            return null;
        }
    }

}
