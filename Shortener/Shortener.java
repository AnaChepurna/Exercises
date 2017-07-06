package com.javarush.task.task33.task3310;

import com.javarush.task.task33.task3310.strategy.StorageStrategy;

/**
 * Created by Ana on 05.07.2017.
 */
public class Shortener {
    private Long lastId = 0l;
    private StorageStrategy storageStrategy;

    public Shortener(StorageStrategy storageStrategy) {
        this.storageStrategy = storageStrategy;
    }

    public synchronized Long getId(String string){
        if (storageStrategy.containsValue(string))
            return storageStrategy.getKey(string);
        lastId++;
        storageStrategy.put(lastId, string);
        return lastId;
    }

    public synchronized String getString(Long id){
        try {
            return storageStrategy.getValue(id);
        }
        catch (Exception ex){
         return null;
        }
    }

}
