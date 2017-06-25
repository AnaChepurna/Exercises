package com.javarush.task.task34.task3413;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SoftCache {
    private Map<Long, SoftReference<AnyObject>> cacheMap = new ConcurrentHashMap<>();

    public AnyObject get(Long key) {
        SoftReference<AnyObject> softReference = cacheMap.get(key);
        if(softReference == null)
            return null;
        return softReference.get();
    }

    public AnyObject put(Long key, AnyObject value) {
        AnyObject last = null;
        if (cacheMap.containsKey(key)){
            last = cacheMap.get(key).get();
            cacheMap.get(key).clear();
        }
        SoftReference<AnyObject> softReference = cacheMap.put(key, new SoftReference<>(value));
        return last;
    }

    public AnyObject remove(Long key) {
        AnyObject last = null;
        if (cacheMap.containsKey(key)){
            last = cacheMap.get(key).get();
            cacheMap.get(key).clear();
        }
        SoftReference<AnyObject> softReference = cacheMap.remove(key);

        return last;
    }
}