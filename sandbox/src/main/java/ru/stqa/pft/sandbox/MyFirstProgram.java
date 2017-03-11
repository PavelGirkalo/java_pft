package ru.stqa.pft.sandbox;

public class MyFirstProgram {
  public static void main(String[] args) {
    hello("world");
    hello("Pavel");
    double l = 16;
    double a = 10;
    double b = 6;

    System.out.println("Площадь квадрата со стороной " + l + " = " + area(l));
    System.out.println("Площадь прямоугольника со сторонами " + a + " и " + b + " = " + area(a,b));

  }

  public static void hello(String string) {

    System.out.println("Hello, " + string + "!");
  }

  public static double area(double len){
    return len*len;
  }

  public static double area(double a, double b){
    return a*b;
  }

}