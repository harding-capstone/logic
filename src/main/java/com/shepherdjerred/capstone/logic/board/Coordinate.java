package com.shepherdjerred.capstone.logic.board;

import com.google.common.base.Preconditions;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class Coordinate {

  private final int x;
  private final int y;

  public Coordinate(int x, int y) {
    Preconditions.checkArgument(x >= 0);
    Preconditions.checkArgument(y >= 0);
    this.x = x;
    this.y = y;
  }

  public Coordinate fromOffset(int xOffset, int yOffset) {
    return new Coordinate(x + xOffset, y + yOffset);
  }

  public Coordinate toLeft() {
    return toLeft(1);
  }

  public Coordinate toRight() {
    return toRight(1);
  }

  public Coordinate above() {
    return above(1);
  }

  public Coordinate below() {
    return below(1);
  }

  public Coordinate toLeft(int i) {
    return new Coordinate(x - i, y);
  }

  public Coordinate toRight(int i) {
    return new Coordinate(x + i, y);
  }

  public Coordinate above(int i) {
    return new Coordinate(x, y + i);
  }

  public Coordinate below(int i) {
    return new Coordinate(x, y - i);
  }

  public boolean isAdjacent(Coordinate c) {
    var diff = Math.abs(x - c.x) + Math.abs(y - c.y);
    return diff == 1;
  }

  public static boolean areCoordinatesCardinal(Coordinate left, Coordinate right) {
    return (left.x != right.x && left.y == right.y)
        || (left.x == right.x && left.y != right.y);
  }

  public static boolean areCoordinatesDiagonal(Coordinate left, Coordinate right) {
    return left.x != right.x
        && left.y != right.y;
  }

  /**
   * Gets the midpoint between two Coordinates.
   * https://www.purplemath.com/modules/midpoint.htm
   */
  public static Coordinate calculateMidpoint(Coordinate left, Coordinate right) {
    if (areCoordinatesDiagonal(left, right)) {
      throw new IllegalArgumentException("Cannot return a midpoint between diagonal coordinates");
    }
    int x = (left.x + right.x) / 2;
    int y = (left.y + right.y) / 2;
    return new Coordinate(x, y);
  }

  /**
   * Calculates the manhattan distance between two Coordinates.
   * https://math.stackexchange.com/questions/139600/how-do-i-calculate-euclidean-and-manhattan-distance-by-hand
   */
  public static int calculateManhattanDistance(Coordinate left, Coordinate right) {
    return Math.abs(left.x - right.x) + Math.abs(left.y - right.y);
  }

  /**
   * Allows directional coordinate checking.
   */
  public Coordinate adjacent(Direction direction, int i) {
    if (direction == Direction.ABOVE) {
      return above(i);
    } else if (direction == Direction.BELOW) {
      return below(i);
    } else if (direction == Direction.RIGHT) {
      return toRight(i);
    } else {
      return toLeft(i);
    }
  }

  public enum Direction {
    ABOVE,
    BELOW,
    RIGHT,
    LEFT
  }
}
