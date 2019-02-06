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

  public static boolean areCoordinatesDiagonal(Coordinate left, Coordinate right) {
    return left.x == right.x || left.x == right.y;
  }

  // https://www.purplemath.com/modules/midpoint.htm
  public static Coordinate getMidpoint(Coordinate left, Coordinate right) {
    int x = (left.x + right.x) / 2;
    int y = (left.y + right.y) / 2;
    return new Coordinate(x, y);
  }

  // https://math.stackexchange.com/questions/139600/how-do-i-calculate-euclidean-and-manhattan-distance-by-hand
  public static int calculateManhattanDistance(Coordinate left, Coordinate right) {
    return Math.abs(left.x - right.x) + Math.abs(left.y - right.y);
  }
}
