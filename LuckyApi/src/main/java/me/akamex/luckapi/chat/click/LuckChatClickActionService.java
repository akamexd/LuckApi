package me.akamex.luckapi.chat.click;

import me.akamex.luckapi.util.function.Optionality;
import me.akamex.luckapi.event.QuickEventListener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LuckChatClickActionService implements ChatClickActionService {

    static final String COMMAND_START = "/luckyapi chatclick ";

    private Map<UUID, ChatClickAction> clickActionMap = new HashMap<>();

    public LuckChatClickActionService(Plugin plugin) {
        QuickEventListener.newListener().event(PlayerCommandPreprocessEvent.class, event -> {
            String message = event.getMessage();
            if(!message.startsWith(COMMAND_START)) {
                return;
            }

            String[] split = message.split(" ");
            if(split.length != 3) {
                return;
            }
            String id = split[2];
            if(id.length() == 0) {
                return;
            }
            UUID uuid;
            try {
                uuid = UUID.fromString(id);
            } catch (IllegalArgumentException exception) {
                exception.printStackTrace();
                return;
            }
            Optionality.optionalOfNullable(clickActionMap.get(uuid)).ifPresent(action -> {
                event.setCancelled(true);
                action.getAction().accept(event.getPlayer());
                if(action.isRemoveOnClick()) {
                    clickActionMap.remove(action.getUuid());
                }
            });
        }).register(plugin);
    }

    @Override
    public ChatClickAction createAction(ChatClickAction action) {
        clickActionMap.put(action.getUuid(), action);
        return action;
    }

    @Override
    public ChatClickAction createAction(ChatClickAction.Builder builder) {
        UUID uuid = UUID.randomUUID();
        while (clickActionMap.containsKey(uuid)) {
            uuid = UUID.randomUUID();
        }
        return createAction(builder.create(uuid));
    }

    @Override
    public void cancel() {
        clickActionMap.clear();
    }
}
