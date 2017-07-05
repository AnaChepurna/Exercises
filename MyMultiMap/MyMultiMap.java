package com.javarush.task.task36.task3610;

import java.io.Serializable;
import java.util.*;

public class MyMultiMap<K, V> extends HashMap<K, V> implements Cloneable, Serializable {
    static final long serialVersionUID = 123456789L;
    private HashMap<K, List<V>> map;
    private int repeatCount;

    public MyMultiMap(int repeatCount) {
        this.repeatCount = repeatCount;
        map = new HashMap<>();
    }

    @Override
    public int size() {
        int size = 0;
        for(Map.Entry<K, List<V>> d: map.entrySet()){
            size += d.getValue().size();
        }
        return size;
    }

    @Override
    public V put(K key, V value) {
        if (!map.containsKey(key)) {
            map.put(key, new ArrayList<V>());
            map.get(key).add(value);
        }
        else if (map.get(key).size() < repeatCount){
            map.get(key).add(value);
            return map.get(key).get(map.get(key).size() - 2);
        }
        else if (map.get(key).size() == repeatCount){
            map.get(key).remove(0);
            map.get(key).add(value);
            return map.get(key).get(map.get(key).size() - 2);
        }
        return null;
    }

    @Override
    public V remove(Object key) {
        V removed = null;
        if (!map.containsKey(key))
            return null;
        else {
            removed = map.get(key).get(0);
            map.get(key).remove(0);
        }
        if (map.get(key).size() == 0)
            map.remove(key);
        return removed;
    }

    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        ArrayList<V> list = new ArrayList<>();
        for (Map.Entry<K, List<V>> d : map.entrySet()){
            for (V s: d.getValue())
                list.add(s);
        }
        return list;
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        for (Map.Entry<K, List<V>> d: map.entrySet()){
            if (d.getValue().contains(value))
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (Map.Entry<K, List<V>> entry : map.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            for (V v : entry.getValue()) {
                sb.append(v);
                sb.append(", ");
            }
        }
        String substring = sb.substring(0, sb.length() - 2);
        return substring + "}";
    }
}