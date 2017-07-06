package com.javarush.task.task33.task3310.tests;

import com.javarush.task.task33.task3310.Helper;
import com.javarush.task.task33.task3310.Shortener;
import com.javarush.task.task33.task3310.strategy.HashBiMapStorageStrategy;
import com.javarush.task.task33.task3310.strategy.HashMapStorageStrategy;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by a.zinov on 23.05.2017.
 */
public class SpeedTest {

    public long getTimeForGettingIds(Shortener shortener, Set<String> strings, Set<Long> ids) {


        long start = new Date().getTime();
        for (String string : strings) {
            ids.add(shortener.getId(string));
        }
        long finish = new Date().getTime();

        return finish - start;

    }

    public long getTimeForGettingStrings(Shortener shortener, Set<Long> ids, Set<String> strings) {

        long start = new Date().getTime();
        for (Long l : ids) {
            strings.add(shortener.getString(l));
        }
        long finish = new Date().getTime();

        return finish - start;
    }

    @Test
    public void testHashMapStorage() {

        Shortener shortener1 = new Shortener(new HashMapStorageStrategy());
        Shortener shortener2 = new Shortener(new HashBiMapStorageStrategy());

        Set<String> origStrings = new HashSet<>();
        for (int i = 0; i < 10000; i++) {
            origStrings.add(Helper.generateRandomString());
        }

        Set<Long> origLong = new HashSet<>();
        long l1 = getTimeForGettingIds(shortener1, origStrings, origLong);
        origLong.clear();
        long l2 = getTimeForGettingIds(shortener2, origStrings, origLong);
        Assert.assertTrue(l1 > l2);

        Set<String> stringSet = new HashSet<>();
        l1 = getTimeForGettingStrings(shortener1, origLong, stringSet);
        stringSet.clear();
        l2 = getTimeForGettingStrings(shortener2, origLong, origStrings);
        Assert.assertEquals(l1, l2, 30);

    }

}