package com.javarush.task.task21.task2113;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ana on 25.06.2017.
 */
public class Hippodrome {
    private List<Horse> horses;
    public static Hippodrome game;

    public Hippodrome(List<Horse> horses){
        this.horses = horses;
    }

    public List<Horse> getHorses() {
        return horses;
    }

    public void run() throws InterruptedException {
        for (int i = 1; i <= 100; i++) {
            move();
            print();
            Thread.sleep(200);
        }
    }

    public void move(){
        for (Horse d: horses)
            d.move();
    }

    public void print(){
        for (Horse d: horses)
            d.print();
        for (int i = 0; i < 10; i++)
            System.out.println();
    }
    
    public Horse getWinner(){
       Horse winner = null;
       double maxDistance = 0;

       for (Horse d: getHorses())
       {
          if (d.getDistance() > maxDistance){
              winner = d;
              maxDistance = d.getDistance();
          }
       }
       return winner;
   }
   public void printWinner()
   {
       System.out.println("Winner is " + getWinner().getName() + "!");
   }
    
    public static void main(String[] args) throws InterruptedException {
        game = new Hippodrome(new ArrayList<>());
        Horse h1 = new Horse("Jo", 3,0);
        Horse h2 = new Horse("Su", 3,0);
        Horse h3 = new Horse("Dima", 3,0);
        game.getHorses().add(h1);
        game.getHorses().add(h2);
        game.getHorses().add(h3);

        game.run();
        game.printWinner();
    }
}
