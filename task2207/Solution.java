package com.javarush.task.task22.task2207;

import javafx.util.Pair;

import java.io.*;
import java.nio.Buffer;
import java.util.LinkedList;
import java.util.List;

/* 
Обращенные слова
*/
public class Solution {
    public static List<Pair> result = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String fileName = reader.readLine();
        reader.close();

        StringBuilder str = new StringBuilder();
        try (BufferedReader fileReader = new BufferedReader(new FileReader(fileName))){
            while (fileReader.ready())
                str.append(fileReader.readLine() + " ");
        }

        String [] words = str.toString().split(" ");
        for (int i = 0; i< words.length; i++){
            StringBuilder wordBuilder = new StringBuilder(words[i]).reverse();
            for (int j = i+1; j < words.length; j++){
                if (wordBuilder.toString().equals(words[j])) {
                    Pair pair = new Pair();
                    pair.first = words[i];
                    pair.second = words[j];
                    if (!(result.contains(pair)))
                        result.add(pair);
                }
            }
        }

        for (Pair d: result)
            System.out.println(d.first + " " + d.second);

    }

    public static class Pair {
        String first;
        String second;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Pair pair = (Pair) o;

            if (first != null ? !first.equals(pair.first) : pair.first != null) return false;
            return second != null ? second.equals(pair.second) : pair.second == null;

        }

        @Override
        public int hashCode() {
            int result = first != null ? first.hashCode() : 0;
            result = 31 * result + (second != null ? second.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return  first == null && second == null ? "" :
                    first == null && second != null ? second :
                    second == null && first != null ? first :
                    first.compareTo(second) < 0 ? first + " " + second : second + " " + first;

        }
    }

}
