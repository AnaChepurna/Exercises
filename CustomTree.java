package com.javarush.task.task20.task2028;


import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*
Все работает и без проблем принимается валидатором.
Реализована возможность добавлять элемент на место удаленного.

Построй дерево

Класс описывающий дерево мы создали, теперь нужен класс описывающий тип элементов дерева:
1.  В классе CustomTree создай вложенный статический параметризированный класс Entry<T> с модификатором доступа по умолчанию.
2. Обеспечь поддержку этим классом интерфейса Serializable.
3. Создай такие поля (модификатор доступа по умолчанию):
— String elementName;
— int lineNumber;
— boolean availableToAddLeftChildren, availableToAddRightChildren;
— Entry<T> parent, leftChild, rightChild;
4. Реализуй публичный конструктор с одним параметром типа String:
— установи поле elementName равным полученному параметру;
— установи поле availableToAddLeftChildren равным true;
— установи поле availableToAddRightChildren равным true;
5. Реализуй метод void checkChildren, устанавливающий поле availableToAddLeftChildren в false, если leftChild не равен null и availableToAddRightChildren в false, если rightChild не равен null.
6. Реализуй метод boolean isAvailableToAddChildren, возвращающий дизъюнкцию полей availableToAddLeftChildren и availableToAddRightChildren.

Любое дерево начинается с корня, поэтому не забудь в класс CustomTree добавить поле root типа Entry<String> c модификатором доступа по умолчанию.
*/
public class CustomTree extends AbstractList<String> implements Cloneable, Serializable{
    public static void main(String[] args) {
        List<String> list = new CustomTree();
        for (int i = 1; i < 9; i++) {
            list.add(String.valueOf(i));
        }
      //  System.out.println("Expected 3, actual is " + ((CustomTree) list).getParent("8"));
    //    System.out.println(list.size());
        list.remove("2");
      //  System.out.println("Expected null, actual is " + ((CustomTree) list).getParent("11"));
        System.out.println(list.size());
    }

    Entry<String> root;

    {
        root = new Entry<>("0");
    }

    @Override
    public String get(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String set(int index, String element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, String element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends String> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    int count;

    @Override
    public int size() {
        count = -1;
        countEntry(root);
        return count;
    }

    private void countEntry(Entry<String> entry){
        count++;
       // System.out.println(entry.elementName);
        if ((entry.leftChild != null) & (!entry.availableToAddLeftChildren))
            countEntry(entry.leftChild);
        if ((entry.rightChild != null) & (!entry.availableToAddRightChildren))
            countEntry(entry.rightChild);
    }

    public String getParent(String s){
        Entry entry = getEntry(s);
        if (entry.parent == null)
            return null;
        return entry.parent.elementName;
    }

    private Entry <String> getEntry(String s){
        ArrayList<Entry> finded = new ArrayList<>();
        getEntry(root,s,finded);
        return finded.get(0);
    }

    private void getEntry(Entry<String> entry, String s, List <Entry> finded){
        if (entry.elementName.equals(s)){
            finded.add(entry);
            return;
        }
        if (entry.leftChild != null)
            getEntry(entry.leftChild, s, finded);
        if (entry.rightChild != null)
            getEntry(entry.rightChild, s, finded);
    }

    @Override
    public boolean add(String s) {
        Entry<String> entry = new Entry<>(s);
        Entry parent = findPlace();
        try {
            entry.setParent(parent);
            if (parent.availableToAddLeftChildren) {
                parent.leftChild = entry;
                parent.availableToAddLeftChildren = false;
            }
            else {
                parent.rightChild = entry;
                parent.availableToAddRightChildren = false;
            }
            return true;
        }
        catch (Exception ex){
            return false;
        }
    }

    private Entry findPlace(){
        ArrayList<Entry> list = new ArrayList<>();
        int min = Integer.MAX_VALUE;
        goTree(root, list);
        for (Entry f: list){
            if (f.lineNumber < min)
                min = f.lineNumber;
        }
        for (Entry d: list){
            if (d.lineNumber == min)
                return d;
        }
        return null;
    }

    private void goTree(Entry entry, ArrayList<Entry> list){
        if (entry.isAvailableToAddChildren()){
            list.add(entry);
        }
        if (entry.leftChild != null){
            goTree(entry.leftChild, list);
        }
        if (entry.rightChild != null){
            goTree(entry.rightChild, list);
        }
    }

    @Override
    public boolean remove(Object o) {
        try{
            String s = (String) o;
            Entry<String> entry = getEntry(s);
            if (entry.parent.leftChild == entry){
                entry.parent.availableToAddLeftChildren = true;
            }
            else {
                entry.parent.availableToAddRightChildren = true;
            }
            if (entry.leftChild != null)
                entry.leftChild.parent = null;
            if (entry.rightChild != null)
                entry.rightChild.parent = null;
            return true;
        }
        catch (Exception ex){
            return false;
        }
    }

    static class Entry<T> implements Serializable
    {

        String elementName;
        int lineNumber;
        boolean availableToAddLeftChildren, availableToAddRightChildren;
        Entry<T> parent, leftChild, rightChild;

        public Entry(String elementName) {
            this.elementName = elementName;
            availableToAddLeftChildren = true;
            availableToAddRightChildren = true;
        }

        public void setParent(Entry<T> parent) {
            this.parent = parent;
            lineNumber = parent.lineNumber + 1;
        }

        void checkChildren()
        {
            if (leftChild != null)
                availableToAddLeftChildren = false;

            if (rightChild != null)
                availableToAddRightChildren = false;
        }

        boolean isAvailableToAddChildren()
        {
            return availableToAddLeftChildren || availableToAddRightChildren;
        }
    }
}
