package ru.stqa.pft.sandbox;

public class MyFirstProgram {
  public static void main(String[] args) {
    /*
    hello("world");
    hello("Pavel");

    Square s = new Square(7);
    System.out.println("Square of a square with side " + s.l + " = " + s.area());

    Rectangle r = new Rectangle(5, 10);
    System.out.println("Square of a rectangle with sides " + r.a + " and " + r.b + " = " + r.area());
    */

    //Вычисление расстояния между точками с помощью функции distance()
    Point p1 = new Point(2.5, 3.5);
    Point p2 = new Point(5.5, -0.5);
    System.out.println("Calculate distance using function");
    System.out.println("Distance between p1(" + p1.a + ";" + p1.b + ") and p2(" + p2.a + ";" + p2.b + ") = " + distance(p1, p2));

  }

  public static void hello(String string) {
    System.out.println("Hello, " + string + "!");
  }

  public static double distance(Point p1, Point p2) {
    return Math.sqrt((p1.a - p2.a)*(p1.a - p2.a)+(p1.b - p2.b)*(p1.b - p2.b));
  }

}