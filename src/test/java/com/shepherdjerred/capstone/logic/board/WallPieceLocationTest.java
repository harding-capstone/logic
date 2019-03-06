package com.shepherdjerred.capstone.logic.board;

import org.junit.Test;

public class WallPieceLocationTest {

  @Test
  public void constructor_createsObject_whenGivenValidCoordinates() {
    var c1 = new Coordinate(0, 0);
    var vertex = new Coordinate(1, 0);
    var c2 = new Coordinate(2, 0);
    new WallLocation(c1, vertex, c2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructor_throwsException_whenFirstCoordinateIsNotAdjacentToVertex() {
    var c1 = new Coordinate(0, 0);
    var vertex = new Coordinate(2, 0);
    var c2 = new Coordinate(3, 0);
    new WallLocation(c1, vertex, c2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructor_throwsException_whenSecondCoordinateIsNotAdjacentToVertex() {
    var c1 = new Coordinate(1, 0);
    var vertex = new Coordinate(2, 0);
    var c2 = new Coordinate(4, 0);
    new WallLocation(c1, vertex, c2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructor_throwsException_whenFirstCoordinateIsNotInLine() {
    var c1 = new Coordinate(0, 1);
    var vertex = new Coordinate(1, 0);
    var c2 = new Coordinate(2, 0);
    new WallLocation(c1, vertex, c2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructor_throwsException_whenSecondCoordinateIsNotInLine() {
    var c1 = new Coordinate(0, 0);
    var vertex = new Coordinate(1, 0);
    var c2 = new Coordinate(2, 1);
    new WallLocation(c1, vertex, c2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructor_throwsException_whenVertexCoordinateIsNotInLine() {
    var c1 = new Coordinate(0, 0);
    var vertex = new Coordinate(1, 1);
    var c2 = new Coordinate(2, 0);
    new WallLocation(c1, vertex, c2);
  }
}
