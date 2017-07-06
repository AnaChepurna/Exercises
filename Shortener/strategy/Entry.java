package com.javarush.task.task33.task3310.strategy;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Ana on 05.07.2017.
 */
public class Entry implements Serializable {
    Long key;
    String value;
    Entry next;
    int hash;

    public Entry(int hash, Long key, String value, Entry next){
        this.hash = hash;
        this.key = key;
        this.value = value;
        this.next = next;
    }

    public Long getKey(){
        return key;
    }

    public String getValue(){
        return value;
    }

    public int hashCode(){
        return Objects.hashCode(key) ^ Objects.hashCode(value);
    }

    public final boolean equals(Object o){
        if (o == this)
            return true;
        if (o instanceof Entry) {
            if (Objects.equals(key, ((Entry) o).getKey()) &&
                    Objects.equals(value, ((Entry) o).getValue()))
                return true;
        }
        return false;
    }

    public String toString(){
        return key + "=" + value;
    }
}
