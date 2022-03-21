package me.akamex.luckapi.menu.filling;

import me.akamex.luckapi.menu.button.MenuButton;
import me.akamex.luckapi.api.Creatable;
import me.akamex.luckapi.menu.MenuSession;

import java.util.HashMap;
import java.util.Map;

@FunctionalInterface
public interface FillingStrategy {

    void generate(ResultBuilder builder, MenuSession session);

    class ResultBuilder implements Creatable<Map<Integer, MenuButton>> {

        private Map<Integer, MenuButton> buttonMap = new HashMap<>();

        private ResultBuilder() {
        }

        public static ResultBuilder newBuilder() {
            return new ResultBuilder();
        }

        public ResultBuilder addButton(MenuButton button, int... slots) {
            for(int slot : slots) {
                buttonMap.put(slot, button);
            }
            return this;
        }

        public Map<Integer, MenuButton> create() {
            return buttonMap;
        }
    }

}
