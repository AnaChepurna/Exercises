package com.javarush.task.task34.task3404;

import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Pattern;

/*
Рекурсия для мат. выражения
*/
public class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.recursion("cos(30 - (30^2)/30 + 30 - 15*2)", 0); //expected output 0.5 6
    }

    public void recursion(final String expression, int countOperation) {
        try {
            Double res = Double.parseDouble(expression);
            System.out.println((String.format(Locale.ENGLISH,"%(.1f", res) + " " + countOperation));
        }
        catch (Exception ex){
            String startExpression = expression;
            String bracketsExpression = bracketsExpression(startExpression);
            ArrayList <Integer> list = new ArrayList<>();
            String resExpression = resExpression(bracketsExpression, 0, list);
            startExpression = startExpression.replace(bracketsExpression,resExpression);
            countOperation += list.size();
            recursion(startExpression,countOperation);
        }
    }


    private String resExpression(String expression, int startCount, ArrayList<Integer> list){
        String [] operations = new String[]{"+", "-", "*", "/", "^", "sin", "cos", "tan"};
        if (expression.contains("("))
            expression = expression.substring(1, expression.indexOf(")"));
        for (int i = startCount; i < operations.length; i++){
            if (expression.contains(operations[i])){
                String [] subexpressions = expression.split(Pattern.quote(operations[i]));
                for (int j = 0; j < subexpressions.length; j++){
                    subexpressions[j] = resExpression(subexpressions[j],i,list);
                }
                double res = 0;
                switch (i) {
                    case (0):
                        for (String d : subexpressions) {
                            res += Double.parseDouble(d);
                        }
                        list.add(1);
                        break;
                    case (1):
                        if (!subexpressions[0].equals(""))
                            res = Double.parseDouble(subexpressions[0]);
                        for (int j = 1; j < subexpressions.length; j++)
                            res -= Double.parseDouble(subexpressions[j]);
                        list.add(2);
                        break;
                    case (2):
                        res = 1;
                        for (String d: subexpressions)
                            res *= Double.parseDouble(d);
                        list.add(1);
                        break;
                    case (3):
                        res = Double.parseDouble(subexpressions[0]);
                        for (int j = 1; j < subexpressions.length; j++)
                            res /= Double.parseDouble(subexpressions[j]);
                        list.add(1);
                        break;
                    case (4):
                        res = 1;
                        for (int j = 0; j < Double.parseDouble(subexpressions[1]); j++)
                            res *= Double.parseDouble(subexpressions[0]);
                        list.add(1);
                        break;
                    case (5):
                        res = Math.sin(Double.parseDouble(subexpressions[1])* 0.0174533);
                        list.add(1);
                        break;
                    case (6):
                        res = Math.cos(Double.parseDouble(subexpressions[1])* 0.0174533);
                        list.add(1);
                        break;
                    case (7):
                        res = Math.tan(Double.parseDouble(subexpressions[1])* 0.0174533);
                        list.add(1);
                        break;
                }
                expression = "" + res;
                break;
            }
        }
        return expression;
    }

    private String bracketsExpression(String startExpression){
        while (startExpression.substring(1).contains("(")){
            startExpression = startExpression.substring(1).substring(startExpression.indexOf("("), startExpression.indexOf(")"));
        }
        if (startExpression.contains(")"))
        startExpression = "(" + startExpression;
        return startExpression;
    }

    public Solution() {
        //don't delete
    }
}
