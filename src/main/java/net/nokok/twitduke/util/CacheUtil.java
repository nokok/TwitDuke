package net.nokok.twitduke.util;

import java.util.HashMap;

public class CacheUtil {
    private static final CacheUtil               instance = new CacheUtil();
    private final        HashMap<String, Object> cacheMap = new HashMap<>(1000);

    private CacheUtil() {

    }

    public static CacheUtil getInstance() {
        return instance;
    }

    public void set(String key, Object value) {
        cacheMap.put(key, value);
    }

    public Object get(String key) {
        return cacheMap.get(key);
    }

    public boolean containsKey(String key) {
        return cacheMap.containsKey(key);
    }
}
