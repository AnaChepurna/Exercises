package com.javarush.task.task37.task3708.retrievers;

import com.javarush.task.task37.task3708.cache.LRUCache;
import com.javarush.task.task37.task3708.storage.Storage;

/**
 * Created by Ana on 16.07.2017.
 */
public class CachingProxyRetriever implements Retriever {
    private LRUCache<Long, Object> cache;
    private OriginalRetriever original;
    private Storage storage;

    public CachingProxyRetriever(Storage storage){
        this.storage = storage;
        original = new OriginalRetriever(storage);
        cache = new LRUCache<>(16);
    }

    @Override
    public Object retrieve(long id) {
        Object obj = cache.find(id);
        if (obj == null) {
            obj = original.retrieve(id);
            cache.set(id, obj);
        }
        return obj;
    }
}
