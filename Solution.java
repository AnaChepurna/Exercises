package com.javarush.task.task20.task2003;

import java.io.*;
import java.util.*;

/* 
Знакомство с properties
*/
public class Solution {
    public static Map<String, String> properties = new HashMap<>();

    public void fillInPropertiesMap() throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String fileName = bufferedReader.readLine();
        bufferedReader.close();
        FileInputStream fileInputStream = new FileInputStream(fileName);
        load(fileInputStream);
    }

    public void save(OutputStream outputStream) throws Exception {
        Properties property = new Properties();
        for (Map.Entry<String, String> d: properties.entrySet()){
            property.setProperty(d.getKey(), d.getValue());
        }
        property.store(outputStream,null);
    }

    public void load(InputStream inputStream) throws Exception {
        Properties property = new Properties();
        property.load(inputStream);
        for (Map.Entry<Object, Object> d: property.entrySet()){
            properties.put((String) d.getKey(), (String) d.getValue());
        }
        inputStream.close();
    }

    public static void main(String[] args) throws Exception {
    }
}
