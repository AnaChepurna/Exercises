package com.javarush.task.task33.task3310.tests;

import com.javarush.task.task33.task3310.Helper;
import com.javarush.task.task33.task3310.Shortener;
import com.javarush.task.task33.task3310.strategy.*;
import org.junit.*;

/**
 * Created by Ana on 06.07.2017.
 */
public class FunctionalTest {

    private void testStorage(Shortener shortener){
        String str1 = Helper.generateRandomString();
        String str2 = Helper.generateRandomString();
        String str3 = "" + str1;

        long res1 = shortener.getId(str1);
        long res2 = shortener.getId(str2);
        long res3 = shortener.getId(str3);

        Assert.assertNotEquals(res1, res2);
        Assert.assertNotEquals(res2, res3);
        Assert.assertEquals(res1, res3);

        String resStr1 = shortener.getString(res1);
        String resStr2 = shortener.getString(res2);
        String resStr3 = shortener.getString(res3);

        Assert.assertEquals(str1, resStr1);
        Assert.assertEquals(str2, resStr2);
        Assert.assertEquals(str3, resStr3);
    }

    @Test
    public void testHashMapStorageStrategy(){
        HashMapStorageStrategy storageStrategy = new HashMapStorageStrategy();
        Shortener shortener = new Shortener(storageStrategy);
        testStorage(shortener);
    }

    @Test
    public void testOurHashMapStorageStrategy(){
        OurHashMapStorageStrategy storageStrategy = new OurHashMapStorageStrategy();
        Shortener shortener = new Shortener(storageStrategy);
        testStorage(shortener);
    }

    @Test
    public void testFileStorageStrategy(){
        FileStorageStrategy storageStrategy = new FileStorageStrategy();
        Shortener shortener = new Shortener(storageStrategy);
        testStorage(shortener);
    }

    @Test
    public void testHashBiMapStorageStrategy(){
        HashBiMapStorageStrategy storageStrategy = new HashBiMapStorageStrategy();
        Shortener shortener = new Shortener(storageStrategy);
        testStorage(shortener);
    }

    @Test
    public void testDualHashBidiMapStorageStrategy(){
        DualHashBidiMapStorageStrategy storageStrategy = new DualHashBidiMapStorageStrategy();
        Shortener shortener = new Shortener(storageStrategy);
        testStorage(shortener);
    }

    @Test
    public void testOurHashBiMapStorageStrategy(){
        OurHashBiMapStorageStrategy storageStrategy = new OurHashBiMapStorageStrategy();
        Shortener shortener = new Shortener(storageStrategy);
        testStorage(shortener);
    }
}

