package com.javarush.task.task37.task3707;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

/**
 * Created by Ana on 01.07.2017.
 */
public class AmigoSet<E> extends AbstractSet<E> implements Serializable, Cloneable, Set<E> {
    private static final Object PRESENT = new Object();
    private transient HashMap<E, Object> map;

    public AmigoSet() {
        map = new HashMap<>();
    }

    public AmigoSet(Collection<? extends E> collection){
        int capacity = Math.max(16, (int) (collection.size()/.75f + 1));
        map = new HashMap<E, Object>(capacity);
        addAll(collection);
    }

    public boolean add(E e){
        return map.put(e, PRESENT) == null;
    }

    @Override
    public Iterator<E> iterator() {
        return map.keySet().iterator();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return map.keySet().contains(o);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public boolean remove(Object o) {
        try {
            map.remove(o);
            return true;
        }
        catch (Exception ex){
            return false;
        }
    }

    @Override
    public AmigoSet<E> clone() throws CloneNotSupportedException {
        try{
            AmigoSet<E> amigoSet = (AmigoSet)super.clone();
            amigoSet.map = (HashMap) map.clone();
            return amigoSet;
        } catch (Exception e){
            throw new InternalError();
        }
    }

    private final void writeObject(ObjectOutputStream x) throws IOException {
        try {
            x.defaultWriteObject();

        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    private final void readObject(ObjectInputStream x) throws IOException {
        try {
            x.defaultReadObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
