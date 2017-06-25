package com.javarush.task.task34.task3403;

/* 
Разложение на множители с помощью рекурсии
*/
public class PrimeDividersSolution {
    public void recursion(int n) {
        int i = 2;
        while (n >= i/2) {
            if (n % i == 0) {
                System.out.println(i + " ");
                recursion(n / i);
                break;
            }
            i++;
        }
    }
}
