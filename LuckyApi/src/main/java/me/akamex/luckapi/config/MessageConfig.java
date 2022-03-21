package me.akamex.luckapi.config;

import me.akamex.luckapi.message.AdaptiveMessage;
import me.akamex.luckapi.message.Message;
import me.akamex.luckapi.message.serialize.MessageSerializers;
import me.akamex.luckapi.message.serialize.YamlMessageSerializer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MessageConfig<P extends MessagePath> extends Config {

    protected P[] paths;
    protected Map<P, Message> messageMap;

    private static final YamlMessageSerializer serializer = MessageSerializers.yaml();

    public MessageConfig(Plugin plugin, String name, P[] paths) {
        super(plugin, name);
        this.paths = paths;

        reload();
    }

    public MessageConfig(Plugin plugin, P[] paths) {
        super(plugin, "language");
        this.paths = paths;

        reload();
    }

    @Override
    protected void load(YamlConfiguration config) {
        messageMap = new HashMap<>();
        for(P path : paths) {
            String stringPath = path.getPath();

            if(config.get(stringPath) == null) {
                String defaultValue = path.getDefaultValue();
                String[] split = defaultValue.split("\n");
                if(split.length >= 2) {
                    config.set(stringPath, Arrays.asList(split));
                } else {
                    config.set(stringPath, defaultValue);
                }
            }

            Message message = serializer.deserialize(config, stringPath);
            if(message == null) {
                throw new NullPointerException();
            }
            messageMap.put(path, message);
        }

        try {
            config.save(file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public Message getMessage(P path) {
        return messageMap.get(path);
    }

    public AdaptiveMessage getAdaptiveMessage(P path) {
        return getMessage(path).toAdaptive();
    }

}
