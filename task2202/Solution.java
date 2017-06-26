package com.javarush.task.task22.task2202;

/* 
Найти подстроку
*/
public class Solution {
    public static void main(String[] args) {
        System.out.println(getPartOfString("JavaRush - лучший сервис Java."));
    }

    public static String getPartOfString(String string) {
        if (string == null)
            throw new TooShortStringException();
        char [] chars = string.toCharArray();
        int firstIndex = 0;
        int lastIndex = 0;
        int spaceCount = 0;
        for (int i = 0; i < chars.length; i++){
            if (chars[i] == ' ')
                spaceCount++;
            if ((chars[i] == ' ') & (spaceCount == 1))
                firstIndex = i+1;
            if ((chars[i] == ' ') & (spaceCount == 5))
                lastIndex = i;
        }
        if (spaceCount < 4)
            throw new TooShortStringException();
        if (spaceCount == 4)
            return string.substring(firstIndex);
        return string.substring(firstIndex,lastIndex);

    }

    public static class TooShortStringException extends RuntimeException {
    }
}
