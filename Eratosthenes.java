package FilteringCollection;

import java.time.chrono.Era;
import java.util.ArrayList;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by Ana on 16.04.2017.
 */
public class Eratosthenes {
    ArrayList<Integer> list = new ArrayList<Integer>();

    public void full(int n) {
        for (int i = 2; i <= n; i++) {
            list.add(i - 2, i);
        }
    }

    public int getFirst(int i) {
         int first = list
                .stream()
                 .skip(i)
                .findFirst()
                .get();
         return first;
    }

    public ArrayList<Integer> getList() {
        return list;
    }

    public void setList(ArrayList<Integer> list) {
        this.list = list;
    }

    public void filtering(int i) {
        list = (ArrayList<Integer>) list
                  .stream()
                  .filter(d -> (d == this.getFirst(i)) | (d % this.getFirst(i) != 0))
                  .collect(Collectors.toList());
    }
    public void getSeive (int n){
        for (int i = 0; i*i <n; i++){
            this.filtering(i);}
    }
}
class Primes {
    public static void main(String[] args) {
        Eratosthenes test = new Eratosthenes();
        int n = 100;
        test.full(n);
        test.getSeive(n);
        ArrayList<Integer>  list = test.getList();
        list.forEach (d-> System.out.println( d));
    }
}
