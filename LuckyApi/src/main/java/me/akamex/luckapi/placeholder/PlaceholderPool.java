package me.akamex.luckapi.placeholder;

import java.util.HashMap;
import java.util.Map;

public class PlaceholderPool {

    private PlaceholderPool() {
        throw new UnsupportedOperationException();
    }

    private static final Map<String, Placeholder<?>> map = new HashMap<>();

    public static Placeholder<?> register(String key, Placeholder<?> format) {
        map.put(key, format);
        return format;
    }

    public static Placeholder<?> get(String key) {
        return map.get(key);
    }

}
