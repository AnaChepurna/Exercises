package com.javarush.task.task33.task3310;

import com.javarush.task.task33.task3310.strategy.*;
import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ana on 05.07.2017.
 */
public class Solution {
    public static void main(String[] args) {
        testStrategy(new OurHashBiMapStorageStrategy(), 10000);
        testStrategy(new HashBiMapStorageStrategy(), 10000);
        testStrategy(new DualHashBidiMapStorageStrategy(),10000);
    }

    public static Set<Long> getIds(Shortener shortener, Set<String> strings){
        HashSet<Long> result = new HashSet<>();
        for (String d: strings){
            result.add(shortener.getId(d));
        }
        return result;
    }

    public static Set<String> getStrings(Shortener shortener, Set<Long> keys){
        HashSet<String> result = new HashSet<>();
        for (Long d: keys){
            result.add(shortener.getString(d));
        }
        return result;
    }

    public static void testStrategy(StorageStrategy strategy, long elementsNumber){
        System.out.println(strategy.getClass().getSimpleName());
        Set<String> test = new HashSet<>();
        for (int i = 0; i< elementsNumber; i++){
            test.add(Helper.generateRandomString());
        }
        Shortener shortener = new Shortener(strategy);

        Date start1 = new Date();
        Set<Long> testresult = getIds(shortener,test);
        Date finish1 = new Date();
        System.out.println(finish1.getTime() - start1.getTime());

        Date start2 = new Date();
        Set<String> testresultresult = getStrings(shortener, testresult);
        Date finish2 = new Date();
        System.out.println(finish2.getTime() - start2.getTime());

        if (test.equals(testresultresult))
            System.out.println("Тест пройден.");
        else
            System.out.println("Тест не пройден.");
    }
}
