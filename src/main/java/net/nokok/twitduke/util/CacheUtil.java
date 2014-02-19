package net.nokok.twitduke.util;

import java.util.HashMap;
import net.nokok.twitduke.main.Config;

public class CacheUtil {
    private static final CacheUtil               instance = new CacheUtil();
    private final        HashMap<String, Object> cacheMap = new HashMap<>(Config.CACHE_SIZE);

    private CacheUtil() {

    }

    public static CacheUtil getInstance() {
        return instance;
    }

    public void set(String key, Object value) {
        if (cacheMap.size() > 1000) {
            cacheMap.clear();
        }
        cacheMap.put(key, value);
    }

    public Object get(String key) {
        return cacheMap.get(key);
    }

    public boolean containsKey(String key) {
        return cacheMap.containsKey(key);
    }
}
