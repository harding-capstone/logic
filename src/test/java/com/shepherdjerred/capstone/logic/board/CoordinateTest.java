package com.shepherdjerred.capstone.logic.board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CoordinateTest {

  @Test(expected = IllegalArgumentException.class)
  public void constructor_ThrowsException_WhenGivenNegativeX() {
    new Coordinate(-1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructor_ThrowsException_WhenGivenNegativeY() {
    new Coordinate(0, -1);
  }

  @Test
  public void fromOffset_ReturnsCoordinateWithOffsetX_WhenXIsPositive() {
    var coordinate = new Coordinate(0, 0);
    var expected = new Coordinate(10, 0);
    var actual = coordinate.fromOffset(10, 0);
    assertEquals(expected, actual);
  }

  @Test
  public void fromOffset_ReturnsCoordinateWithOffsetY_WhenYIsPositive() {
    var coordinate = new Coordinate(0, 0);
    var expected = new Coordinate(0, 10);
    var actual = coordinate.fromOffset(0, 10);
    assertEquals(expected, actual);
  }

  @Test
  public void fromOffset_ReturnsCoordinateWithOffsetXAndY_WhenXAndYArePositive() {
    var coordinate = new Coordinate(0, 0);
    var expected = new Coordinate(10, 10);
    var actual = coordinate.fromOffset(10, 10);
    assertEquals(expected, actual);
  }

  @Test
  public void fromOffset_ReturnsEqualCoordinate_WhenXAndYAreZero() {
    var coordinate = new Coordinate(0, 0);
    var actual = coordinate.fromOffset(0, 0);
    assertEquals(coordinate, actual);
  }

  @Test
  public void left_ReturnsCoordinateWithXDecremented_WhenCalledWithNoArgument() {
    var coordinate = new Coordinate(5, 0);
    var expected = new Coordinate(4, 0);
    var actual = coordinate.toLeft();
    assertEquals(expected, actual);
  }

  @Test
  public void right_ReturnsCoordinateWithXIncremented_WhenCalledWithNoArgument() {
    var coordinate = new Coordinate(5, 0);
    var expected = new Coordinate(6, 0);
    var actual = coordinate.toRight();
    assertEquals(expected, actual);
  }

  @Test
  public void above_ReturnsCoordinateWithYIncremented_WhenCalledWithNoArgument() {
    var coordinate = new Coordinate(0, 5);
    var expected = new Coordinate(0, 6);
    var actual = coordinate.above();
    assertEquals(expected, actual);
  }

  @Test
  public void below_ReturnsCoordinateWithYDecremented_WhenCalledWithNoArgument() {
    var coordinate = new Coordinate(0, 5);
    var expected = new Coordinate(0, 4);
    var actual = coordinate.below();
    assertEquals(expected, actual);
  }

  @Test
  public void isAdjacent_ReturnsTrue_WhenCalledWithCoordinateOneAbove() {
    var c1 = new Coordinate(0, 5);
    var c2 = new Coordinate(0, 6);
    assertTrue(c1.isAdjacent(c2));
  }

  @Test
  public void isAdjacent_ReturnsTrue_WhenCalledWithCoordinateOneBelow() {
    var c1 = new Coordinate(0, 5);
    var c2 = new Coordinate(0, 4);
    assertTrue(c1.isAdjacent(c2));
  }

  @Test
  public void isAdjacent_ReturnsTrue_WhenCalledWithCoordinateOneToTheLeft() {
    var c1 = new Coordinate(5, 0);
    var c2 = new Coordinate(4, 0);
    assertTrue(c1.isAdjacent(c2));
  }

  @Test
  public void isAdjacent_ReturnsTrue_WhenCalledWithCoordinateOneToTheRight() {
    var c1 = new Coordinate(5, 0);
    var c2 = new Coordinate(6, 0);
    assertTrue(c1.isAdjacent(c2));
  }

  @Test
  public void isAdjacent_ReturnsFalse_WhenCalledWithDiagonallyAdjacentCoordinate() {
    var origin = new Coordinate(5, 5);
    var topLeft = new Coordinate(4, 6);
    var topRight = new Coordinate(6, 6);
    var bottomLeft = new Coordinate(4, 4);
    var bottomRight = new Coordinate(6, 4);

    assertFalse(origin.isAdjacent(topLeft));
    assertFalse(origin.isAdjacent(topRight));
    assertFalse(origin.isAdjacent(bottomLeft));
    assertFalse(origin.isAdjacent(bottomRight));
  }

  @Test
  public void isAdjacent_ReturnsFalse_WhenCalledWithCoordinateTwoAway() {
    var origin = new Coordinate(5, 5);
    var twoLeft = new Coordinate(3, 5);
    var twoRight = new Coordinate(7, 5);
    var twoAbove = new Coordinate(5, 7);
    var twoBelow = new Coordinate(5, 3);

    assertFalse(origin.isAdjacent(twoLeft));
    assertFalse(origin.isAdjacent(twoRight));
    assertFalse(origin.isAdjacent(twoAbove));
    assertFalse(origin.isAdjacent(twoBelow));
  }

  @Test
  public void areCoordinatesCardinal_ReturnsTrue_WhenCalledWithCardinalCoordinates() {
    var origin = new Coordinate(5, 5);
    var oneLeft = new Coordinate(4, 5);
    var twoLeft = new Coordinate(3, 5);
    var oneRight = new Coordinate(6, 5);
    var twoRight = new Coordinate(7, 5);
    var oneAbove = new Coordinate(5, 6);
    var twoAbove = new Coordinate(5, 7);
    var oneBelow = new Coordinate(5, 4);
    var twoBelow = new Coordinate(5, 3);

    assertTrue(Coordinate.areCoordinatesCardinal(origin, oneLeft));
    assertTrue(Coordinate.areCoordinatesCardinal(origin, twoLeft));
    assertTrue(Coordinate.areCoordinatesCardinal(origin, oneRight));
    assertTrue(Coordinate.areCoordinatesCardinal(origin, twoRight));
    assertTrue(Coordinate.areCoordinatesCardinal(origin, oneAbove));
    assertTrue(Coordinate.areCoordinatesCardinal(origin, twoAbove));
    assertTrue(Coordinate.areCoordinatesCardinal(origin, oneBelow));
    assertTrue(Coordinate.areCoordinatesCardinal(origin, twoBelow));
  }

  @Test
  public void areCoordinatesCardinal_ReturnsFalse_WhenCalledWithDiagonalCoordinates() {
    var origin = new Coordinate(5, 5);
    var topLeft = new Coordinate(4, 6);
    var topRight = new Coordinate(6, 6);
    var bottomLeft = new Coordinate(4, 4);
    var bottomRight = new Coordinate(6, 4);

    var c1 = new Coordinate(10, 10);
    var c2 = new Coordinate(6, 10);

    assertFalse(Coordinate.areCoordinatesCardinal(origin, topLeft));
    assertFalse(Coordinate.areCoordinatesCardinal(origin, topRight));
    assertFalse(Coordinate.areCoordinatesCardinal(origin, bottomLeft));
    assertFalse(Coordinate.areCoordinatesCardinal(origin, bottomRight));
    assertFalse(Coordinate.areCoordinatesCardinal(origin, c1));
    assertFalse(Coordinate.areCoordinatesCardinal(origin, c2));
  }

  @Test
  public void areCoordinatesDiagonal_ReturnsTrue_WhenCalledWithDiagonalCoordinates() {
    var origin = new Coordinate(5, 5);
    var topLeft = new Coordinate(4, 6);
    var topRight = new Coordinate(6, 6);
    var bottomLeft = new Coordinate(4, 4);
    var bottomRight = new Coordinate(6, 4);

    var c1 = new Coordinate(10, 10);
    var c2 = new Coordinate(6, 10);

    assertTrue(Coordinate.areCoordinatesDiagonal(origin, topLeft));
    assertTrue(Coordinate.areCoordinatesDiagonal(origin, topRight));
    assertTrue(Coordinate.areCoordinatesDiagonal(origin, bottomLeft));
    assertTrue(Coordinate.areCoordinatesDiagonal(origin, bottomRight));
    assertTrue(Coordinate.areCoordinatesDiagonal(origin, c1));
    assertTrue(Coordinate.areCoordinatesDiagonal(origin, c2));
  }

  @Test
  public void areCoordinatesDiagonal_ReturnsFalse_WhenCalledWithCardinalCoordinates() {
    var origin = new Coordinate(5, 5);
    var oneLeft = new Coordinate(4, 5);
    var twoLeft = new Coordinate(3, 5);
    var oneRight = new Coordinate(6, 5);
    var twoRight = new Coordinate(7, 5);
    var oneAbove = new Coordinate(5, 6);
    var twoAbove = new Coordinate(5, 7);
    var oneBelow = new Coordinate(5, 4);
    var twoBelow = new Coordinate(5, 3);

    assertFalse(Coordinate.areCoordinatesDiagonal(origin, oneLeft));
    assertFalse(Coordinate.areCoordinatesDiagonal(origin, twoLeft));
    assertFalse(Coordinate.areCoordinatesDiagonal(origin, oneRight));
    assertFalse(Coordinate.areCoordinatesDiagonal(origin, twoRight));
    assertFalse(Coordinate.areCoordinatesDiagonal(origin, oneAbove));
    assertFalse(Coordinate.areCoordinatesDiagonal(origin, twoAbove));
    assertFalse(Coordinate.areCoordinatesDiagonal(origin, oneBelow));
    assertFalse(Coordinate.areCoordinatesDiagonal(origin, twoBelow));
  }

  @Test
  public void calculateMidpoint_ReturnsMidpoint_WhenCalledWithCardinalPoints() {
    var c1 = new Coordinate(5, 5);
    var c2 = new Coordinate(5, 7);
    var expected = new Coordinate(5, 6);

    assertEquals(expected, Coordinate.calculateMidpoint(c1, c2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void calculateMidpoint_ThrowsException_WhenCalledWithDiagonalPoints() {
    var c1 = new Coordinate(5, 5);
    var c2 = new Coordinate(6, 6);

    Coordinate.calculateMidpoint(c1, c2);
  }

  @Test
  public void calculateManhattanDistance_ReturnsManhattanDistance_WhenCalledWithCardinalPoints() {
    var c1 = new Coordinate(5, 5);
    var c2 = new Coordinate(5, 6);
    var c3 = new Coordinate(5, 7);

    assertEquals(1, Coordinate.calculateManhattanDistance(c1, c2));
    assertEquals(2, Coordinate.calculateManhattanDistance(c1, c3));
  }

  @Test
  public void calculateManhattanDistance_ReturnsManhattanDistance_WhenCalledWithDiagonalPoints() {
    var c1 = new Coordinate(5, 5);
    var c2 = new Coordinate(6, 6);
    var c3 = new Coordinate(7, 7);

    assertEquals(2, Coordinate.calculateManhattanDistance(c1, c2));
    assertEquals(4, Coordinate.calculateManhattanDistance(c1, c3));
  }
}
