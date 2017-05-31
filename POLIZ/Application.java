package POLIZ;

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Created by Ana on 31.05.2017.
 */
public class Application {
    public static double parse(String rpnString){
        StackPoliz stack = new StackPoliz();
        String[] strSplited = rpnString.trim().split(" ");

        for (int i = 0; i < strSplited.length; i++){
            double number = 0;
            if (isNumber(strSplited[i])){
                number = Double.parseDouble(strSplited[i]);
                stack.push(number);
            }
            else switch (strSplited[i]){
                case ("+"):
                    number = stack.pop() + stack.pop();
                    stack.push(number);
                    break;
                case ("-"):
                    double num3 = stack.pop();
                    double num4 = stack.pop();
                    number = num4 - num3;
                    stack.push(number);
                    break;
                case ("*"):
                    number = stack.pop() * stack.pop();
                    stack.push(number);
                    break;
                case ("/"):
                    double num1 = stack.pop();
                    double num2 = stack.pop();
                    if ((num1 == 0))
                        throw new ArithmeticException();
                    number = num2 / num1;
                    stack.push(number);
                    break;
                default:
                    throw new RPNParserException();
            }

        }
        return stack.pop();
    }

    private static boolean isNumber(String obj){
        if (obj.equals(""))
            return false;
        if ((obj.length() > 1) & obj.substring(0,1).equals("-"))
            obj = obj.replace("-", "");
        if (obj.replaceAll("\\d","").replace(".","").equals(""))
            return true;
        return false;
    }

    public static void main(String[] args) {
         System.out.println(parse("12 2 3 4 * 10 5 / - * +"));
    }

}

class StackPoliz {
    LinkedList <Double> list;

    StackPoliz(){
        list = new LinkedList();
    }

    public void push(Double obj){
        list.add(obj);
    }

    public Double pop() throws NoSuchElementException{
       try {
           double doub = list.getLast();
           list.removeLast();
           return doub;
       }
       catch (NoSuchElementException ex){
           throw new RPNParserException();
       }
    }
}
