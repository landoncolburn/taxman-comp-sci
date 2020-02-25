/*
  Landon Colburn
  ©2019
*/

import java.util.*;
import java.io.*;
import java.awt.*;

public class Taxman {

  public static ArrayList<Number> numbers = new ArrayList<Number>();
  private static Scanner scan = new Scanner(System.in);
  private static int taxmanScore = 0;
  private static int playerScore = 0;

  public static void main(String[] args) {
    System.out.println("How many numbers?");
    int n = scan.nextInt();
    for(int i = 0; i<n; i++){
      numbers.add(new Number(i+1));
    }
    beatTaxman();
    printNumbers();
    System.out.println("You: "+playerScore);
    System.out.println("Taxman: "+taxmanScore);
  }

  public static void init(int n){
    for(int i = 0; i<n; i++){
      numbers.add(new Number(i+1));
    }
  }

  public static void beatTaxman(){

    boolean happened = false;

    //FIRST MOVE
    for(int i = numbers.size()-1; i>0; i--){
      if(numbers.get(i).isPrime() && (numbers.get(i).getState() == State.ACTIVE)){
        if(!happened){
          taxmanTurn(take(numbers.get(i)));
          happened = true;
        } else {
          numbers.get(i).setState(State.UNREACHABLE);
        }
      }
    }

    //REMAINING MOVES
    for(int i = numbers.size()-1; i>=(numbers.size()-1)/2; i--){
      if((numbers.get(i).getNumber()%2==1) && (numbers.get(i).getState()==State.ACTIVE) && (hasValidFactors(numbers.get(i)))){
        taxmanTurn(take(numbers.get(i)));
      }
    }

    for(int i = (numbers.size()-1)/2; i<numbers.size(); i++){
      if((numbers.get(i).getNumber()%2==0) && (numbers.get(i).getState()==State.ACTIVE) && (hasValidFactors(numbers.get(i)))){
        taxmanTurn(take(numbers.get(i)));
      }
    }

    for(int i = 0; i<numbers.size(); i++){
      if(numbers.get(i).getState() == State.ACTIVE || numbers.get(i).getState() == State.UNREACHABLE){
        tax(numbers.get(i));
      }
    }
  }

  public static Number take(Number n){
    if(n.getState()!=State.ACTIVE){
      System.err.println("Error Inactive Number");
    } else if(!hasValidFactors(n)){
      System.err.println("Error Has No Factors");
    } else {
      playerScore += n.getNumber();
      n.setState(State.SELECTED);
      System.out.println("You take " + n.getNumber());
    }
    return n;
  }

  public static void taxmanTurn(Number nu){
    ArrayList<Number> f = factors(nu);
    for(Number n : f){
      if(n.getState() == State.ACTIVE || n.getState() == State.UNREACHABLE){
        tax(n);
        System.out.println("Taxman takes " + n.getNumber());
      }
    }
    clean();
  }

  public static Number intToNumber(int i){
    return numbers.get(i-1);
  }

  public static void playerTurn(Number n){
    take(n);
    taxmanTurn(n);
  }

  public static void clean(){
    for(Number n : numbers){
      if(!hasValidFactors(n) && n.getState()==State.ACTIVE){
        n.setState(State.UNREACHABLE);
      }
    }
  }

  public static void tax(Number n){
    if(n.getState()==State.SELECTED||n.getState()==State.TAKEN){
      System.err.println("Error Active Number");
    } else {
      taxmanScore += n.getNumber();
      n.setState(State.TAKEN);
    }
  }

  public static ArrayList<Number> factors(Number n){
    ArrayList<Number> l = new ArrayList<Number>();
    for(int i = 1; i<n.getNumber(); i++){
      if(n.getNumber() % i == 0){
        l.add(numbers.get(i-1));
      }
    }
    return l;
  }

  public static boolean hasValidFactors(Number n){
    ArrayList<Number> fac = factors(n);
    for(Number nu : fac){
      if(nu.getState() == State.ACTIVE || nu.getState() == State.UNREACHABLE){
        return true;
      }
    }
    return false;
  }

  public static void printFinal(){
    System.out.println("Taxman: " + taxmanScore);
    System.out.println("Player: " + playerScore);
  }

  public static void printNumbers(){
    for(Number n : numbers){
      System.out.println("Number: " + n.getNumber() + ", " + n.getState().toString());
    }
  }

}
