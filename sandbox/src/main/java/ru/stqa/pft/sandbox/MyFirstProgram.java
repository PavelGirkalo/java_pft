package ru.stqa.pft.sandbox;

public class MyFirstProgram {
  public static void main(String[] args) {
    hello("world");
    hello("Pavel");

    Square s = new Square(7);
    System.out.println("Square of a square with side " + s.l + " = " + s.area());

    Rectangle r = new Rectangle(5, 10);
    System.out.println("Square of a rectangle with sides " + r.a + " and " + r.b + " = " + r.area());

  }

  public static void hello(String string) {

    System.out.println("Hello, " + string + "!");
  }

}