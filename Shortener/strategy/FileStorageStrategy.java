package com.javarush.task.task33.task3310.strategy;


public class FileStorageStrategy implements StorageStrategy{

    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final long DEFAULT_BUCKET_SIZE_LIMIT = 10000;
    private FileBucket[] table = new FileBucket[DEFAULT_INITIAL_CAPACITY];
    private int size;
    private long bucketSizeLimit = DEFAULT_BUCKET_SIZE_LIMIT;
    private long maxBucketSize;

    public FileStorageStrategy() {
        for (int i = 0; i < table.length; i++) {
            table[i] = new FileBucket();
        }
    }

    public long getBucketSizeLimit() {
        return bucketSizeLimit;
    }

    public void setBucketSizeLimit(long bucketSizeLimit) {
        this.bucketSizeLimit = bucketSizeLimit;
    }

    public int hash(Long k){
//        k ^= (k >>> 20) ^ (k >>> 12);
//        return (int)(k ^ (k >>> 7) ^ (k >>> 4));
        return k.hashCode();
    }

    public int indexFor(int hash, int length){
        return hash & (length-1);
    }

    public Entry getEntry(Long key){

        int hash = (key == null) ? 0 : hash((long)key.hashCode());
        for (Entry e = table[indexFor(hash, table.length)].getEntry();
             e != null;
             e = e.next) {
            Object k;
            if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k))))
                return e;
        }
        return null;
    }

    public void resize(int newCapacity){
        FileBucket[] newTable = new FileBucket[newCapacity];
        transfer(newTable);
        table = newTable;
    }

    public void transfer(FileBucket[] newTable){
        int newCapacity = newTable.length;
        for (FileBucket fb : table){
            if (fb == null)
                continue;
            Entry e = fb.getEntry();
            while( null != e){
                Entry next = e.next;
                int i = indexFor(e.hash, newCapacity);
                if (newTable[i] == null)
                    newTable[i] = new FileBucket();
                e.next = newTable[i].getEntry();
                newTable[i] = new FileBucket();
                newTable[i].putEntry(e);
                e = next;
            }
            fb.remove();
        }

//        FileBucket[] src = table;
//        int newCapacity = newTable.length;
//        for (int j = 0; j < table.length; j++) {
//            Entry e = table[j].getEntry();
//            if (e != null) {
//                table[j] = null;
//                do {
//                    Entry next = e.next;
//                    int i = indexFor(e.hash, newCapacity);
//                    e.next = newTable[i].getEntry();
//                    newTable[i].putEntry(e);
//                    e = next;
//                } while (e != null);
//
//                table[j].remove();
//            }
//        }
    }

    public void addEntry(int hash, Long key, String value, int bucketIndex){
        final Entry e = table[bucketIndex].getEntry();
        table[bucketIndex].putEntry(new Entry(hash, key, value, e));
        size++;
        long currentBucketSize = table[bucketIndex].getFileSize();
        maxBucketSize = currentBucketSize > maxBucketSize ? currentBucketSize : maxBucketSize;
        if (maxBucketSize > bucketSizeLimit) resize(2 * table.length);
    }

    public void createEntry(int hash, Long key, String value, int bucketIndex){
        table[bucketIndex].putEntry(new Entry(hash, key, value, null));
        size++;
        long currentBucketSize = table[bucketIndex].getFileSize();
        maxBucketSize = currentBucketSize > maxBucketSize ? currentBucketSize : maxBucketSize;
    }

    @Override
    public boolean containsKey(Long key) {
        return getEntry(key) != null;
    }

    @Override
    public boolean containsValue(String value) {

        FileBucket[] tab = table;
        for (int i = 0; i < tab.length ; i++) {
            if (table[i] == null) continue;

            for (Entry e = tab[i].getEntry(); e != null; e = e.next)
                if (value.equals(e.value))
                    return true;
        }
        return false;
    }

    @Override
    public void put(Long key, String value) {

        int hash = hash((long)key.hashCode());
        int i = indexFor(hash, table.length);
//        if (table[i] != null) {

        for (Entry e = table[i].getEntry(); e != null; e = e.next) {
            Object k;
            if (e.hash == hash && ((k = e.key) == key || key.equals(k))) {
//                String oldValue = e.value;
                e.value = value;
            }
        }
        addEntry(hash, key, value, i);
//        }

    }

    @Override
    public Long getKey(String value) {
        FileBucket[] tab = table;
        for (int i = 0; i < tab.length ; i++)
            for (Entry e = tab[i].getEntry() ; e != null ; e = e.next)
                if (value.equals(e.value))
                    return e.getKey();
        return null;
    }

    @Override
    public String getValue(Long key) {

        return getEntry(key).getValue();
    }
}