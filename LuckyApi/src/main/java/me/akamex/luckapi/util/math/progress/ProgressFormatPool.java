package me.akamex.luckapi.util.math.progress;

import java.util.HashMap;
import java.util.Map;

public class ProgressFormatPool {

    private ProgressFormatPool() {
        throw new UnsupportedOperationException();
    }

    private static final Map<String, ProgressFormat> map = new HashMap<>();

    public static ProgressFormat register(String key, ProgressFormat format) {
        map.put(key, format);
        return format;
    }

    public static ProgressFormat get(String key) {
        return map.get(key);
    }

}
