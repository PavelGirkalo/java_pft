package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {

  @Test
  public void testDistance1(){
    Point p1 = new Point(0,0);
    Point p2 = new Point(3,4);
    Assert.assertEquals(p1.distance(p2), 5.0);
  }

  @Test
  public void testDistance2(){
    Point p1 = new Point(-1,-1);
    Point p2 = new Point(2,3);
    Assert.assertEquals(p1.distance(p2), 5.0);
  }

  @Test
  public void testDistance3(){
    Point p1 = new Point(0,0);
    Point p2 = new Point(0,0);
    Assert.assertEquals(p1.distance(p2), 0.0);
  }

}
