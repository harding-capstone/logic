package com.shepherdjerred.capstone.logic.board;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public final class Coordinate {

  private final int x;
  private final int y;

  public Coordinate fromOffset(int xOffset, int yOffset) {
    return new Coordinate(x + xOffset, y + yOffset);
  }

  public Coordinate toLeft() {
    return new Coordinate(x - 1, y);
  }

  public Coordinate toRight() {
    return new Coordinate(x + 1, y);
  }

  public Coordinate above() {
    return new Coordinate(x, y + 1);
  }

  public Coordinate below() {
    return new Coordinate(x, y - 1);
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

  /**
   * Allows directional coordinate checking
   */
  public Coordinate adjacent(Direction direction, int i) {
    if (direction == Direction.above) {
      return above(i);
    } else if (direction == Direction.below) {
      return below(i);
    } else if (direction == Direction.right) {
      return toRight(i);
    } else {
      return toLeft(i);
    }
  }

  /**
   * Checks if two Coordinates are diagonal to each other
   */
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
   *
   * https://www.purplemath.com/modules/midpoint.htm
   */
  public static Coordinate getMidpoint(Coordinate left, Coordinate right) {
    if (areCoordinatesDiagonal(left, right)) {
      throw new IllegalArgumentException("Cannot return a midpoint between diagonal coordinates");
    }
    int x = (left.x + right.x) / 2;
    int y = (left.y + right.y) / 2;
    return new Coordinate(x, y);
  }

  /**
   * Calculates the manhattan distance between two Coordinates.
   *
   * https://math.stackexchange.com/questions/139600/how-do-i-calculate-euclidean-and-manhattan-distance-by-hand
   */
  public static int calculateManhattanDistance(Coordinate left, Coordinate right) {
    return Math.abs(left.x - right.x) + Math.abs(left.y - right.y);
  }

  public enum Direction {
    above,
    below,
    right,
    left
  }
}
