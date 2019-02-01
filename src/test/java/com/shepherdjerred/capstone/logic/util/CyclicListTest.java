package com.shepherdjerred.capstone.logic.util;

import org.junit.Assert;
import org.junit.Test;

public class CyclicListTest {
  @Test
  public void current_WhenListHasOneNode_ReturnsHead() {
    var list = new CyclicList<Integer>();
    list.add(1);
    Assert.assertEquals(1, (int) list.getCurrent());
  }

  @Test
  public void current_WhenListHasTwoNodes_ReturnsHead() {
    var list = new CyclicList<Integer>();
    list.add(1);
    list.add(2);
    Assert.assertEquals(1, (int) list.getCurrent());
  }

  @Test
  public void current_WhenListHasOneNodeAndHasNextedOnce_ReturnsHead() {
    var list = new CyclicList<Integer>();
    list.add(1);
    list.next();
    Assert.assertEquals(1, (int) list.getCurrent());
  }

  @Test
  public void current_WhenListHasTwoNodeAndHasNextedOnce_ReturnsTail() {
    var list = new CyclicList<Integer>();
    list.add(1);
    list.add(2);
    list.next();
    Assert.assertEquals(2, (int) list.getCurrent());
  }

  @Test
  public void current_WhenListHasTwoNodeAndHasNextedTwice_ReturnsHead() {
    var list = new CyclicList<Integer>();
    list.add(1);
    list.add(2);
    list.next();
    list.next();
    Assert.assertEquals(1, (int) list.getCurrent());
  }

}
